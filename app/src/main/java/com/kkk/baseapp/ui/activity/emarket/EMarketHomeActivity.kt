package com.kkk.baseapp.ui.activity.emarket

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kkk.baseapp.R
import com.kkk.baseapp.data.vos.EMarketShopProductListVO
import com.kkk.baseapp.databinding.ActivityEmarketHomeBinding
import com.kkk.baseapp.ui.adapter.displayer.emarket.EMarketStoreItemDisplayer
import com.kkk.baseapp.util.PreferenceConstants
import com.kkk.baseapp.viewmodel.EMarketViewModel
import com.kkk.mylibrary.network.ResourceState
import com.kkk.mylibrary.ui.activity.BaseViewBindingActivity
import com.kkk.mylibrary.ui.adapter.DelegateAdapter
import com.kkk.mylibrary.ui.adapter.displayer.ItemDisplayer
import com.kkk.mylibrary.utils.extensions.getStringData
import com.kkk.mylibrary.utils.extensions.setStringData
import com.kkk.mylibrary.utils.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.reflect.Type

@AndroidEntryPoint
class EMarketHomeActivity : BaseViewBindingActivity<ActivityEmarketHomeBinding>() {

    companion object{
        private const val IE_IS_EDITING = "IE_IS_EDITING"
        fun newIntent(context: Context, isEditing:Boolean?=false): Intent {
            val intent = Intent(context, EMarketHomeActivity::class.java)
            intent.putExtra(IE_IS_EDITING,isEditing)
            return intent
        }
    }

    private var isShow = true
    private var scrollRange = -1

    private var cartProductList:MutableList<EMarketShopProductListVO> = mutableListOf()
    private var mItemDataList = mutableListOf<EMarketShopProductListVO>()
    private var mItemList = mutableListOf<ItemDisplayer>()
    private val mAdapter: DelegateAdapter = DelegateAdapter()
    private var isSelectedAll:Boolean = false
    private var isEditing:Boolean = false

//    private val mViewModel: EMarketViewModel by viewModel()
    private val mViewModel by lazy {
        ViewModelProvider(this)[EMarketViewModel::class.java]
}

    override val bindingInflater: (LayoutInflater) -> ActivityEmarketHomeBinding
        get() = ActivityEmarketHomeBinding::inflate

    override fun setup() {
        setupUI()
        mViewModel.getAllStoreInfo()
        isEditing = intent.getBooleanExtra(IE_IS_EDITING,false)
        if (!isEditing){
            mViewModel.setCardItemList(ArrayList())
        }
//        lifecycleScope.launch {
////            setStringData(PreferenceConstants.DATASTORE_E_MARKET_ORDER_ITEM_CART,Gson().toJson(ArrayList<EMarketShopProductListVO>()))
//            deleteStringData(PreferenceConstants.DATASTORE_E_MARKET_ORDER_ITEM_CART)
//        }
    }

    override fun onResume() {
        super.onResume()
        observeCartItemData()
    }

    private fun setupUI(){
        setupToolbar()
        setupRecyclerView()
    }

    private fun setupToolbar(){
        binding.apply {
            tbToolbar.tvToolbarTitle.text = " "
            tbToolbar.tbBackArrow.background =
                Color.TRANSPARENT.toDrawable()
            tbToolbar.tbBackArrow.setNavigationOnClickListener {
                finish()
            }

            appBarLayout.addOnOffsetChangedListener { barLayout, verticalOffset ->
                if (scrollRange == -1) {
                    scrollRange = barLayout?.totalScrollRange!!
                }
                if (scrollRange + verticalOffset == 0) {
                    tbToolbar.tvToolbarTitle.text = "E-Market"
                    isShow = true
                } else if (isShow) {
                    tbToolbar.tvToolbarTitle.text = " "
                    isShow = false
                }
            }
        }
    }

    private fun setupRecyclerView(){
        binding.rvShopProductList.apply{
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
            layoutManager = GridLayoutManager(context, 1)
            adapter = mAdapter
        }
    }

    override fun observers() {
        mViewModel.storeInfoLD.observe(this){
            binding.loadingView.root.visibility =
                if (it is ResourceState.Loading) View.VISIBLE else View.GONE
            when(it){
                is ResourceState.Loading, ResourceState.Initial -> {

                }
                is ResourceState.Success -> {
                    binding.apply {
                        val data = it.successData
                        tvStoreName.text = data.name
                        rbStore.rating = data.rating.toFloat()
                        tvStoreOpeningTime.text = getString(R.string.txt_opening_time_to,data.openingTime,data.closingTime)
                    }
                }
                is ResourceState.SystemError -> {
                    showToast(it.error)
                }
                is ResourceState.HttpError -> {
                    showToast(it.error)
                }
                else -> {

                }
            }
        }
        mViewModel.storeProductListLD.observe(this){
            binding.loadingView.root.visibility =
                if (it is ResourceState.Loading) View.VISIBLE else View.GONE
            when(it){
                is ResourceState.Loading, ResourceState.Initial -> {

                }
                is ResourceState.Success -> {
                    binding.apply {
                        mItemDataList = it.successData as MutableList<EMarketShopProductListVO>
                        mItemDataList.mapIndexed { index,item ->
                            mItemList.add(EMarketStoreItemDisplayer(index,item,::onClickCheckBox,::onClickCounter))
                        }
                        mAdapter.setData(mItemList)
                        observeCartItemData()
                    }
                }
                is ResourceState.SystemError -> {
                    showToast(it.error)
                }
                is ResourceState.HttpError -> {
                    showToast(it.error)
                }
                else -> {

                }
            }
        }
        mViewModel.cartProductListLD.observe(this){
            binding.loadingView.root.visibility =
                if (it is ResourceState.Loading) View.VISIBLE else View.GONE
            when(it){
                is ResourceState.Loading, ResourceState.Initial -> {

                }
                is ResourceState.Success -> {
                    lifecycleScope.launch {
                        val jsonString = Gson().toJson(it.successData)
                        setStringData(PreferenceConstants.DATASTORE_E_MARKET_ORDER_ITEM_CART,jsonString)
                    }

                }
                is ResourceState.SystemError -> {
                    showToast(it.error)
                }
                is ResourceState.HttpError -> {
                    showToast(it.error)
                }
                else -> {

                }
            }
        }
        observeCartItemData()
    }

    private fun observeCartItemData(){
        lifecycleScope.launch {
            getStringData(PreferenceConstants.DATASTORE_E_MARKET_ORDER_ITEM_CART)
                .collectLatest { cartItemListString ->
                    cartItemListString?.let {
                        val listType: Type =
                            object : TypeToken<ArrayList<EMarketShopProductListVO?>?>() {}.type
                        val cartCache:ArrayList<EMarketShopProductListVO> = Gson().fromJson(it, listType)
                        binding.apply {
                            cartProductList = cartCache
                            binding.btnCart.apply {
                                text = resources.getQuantityString(R.plurals.txt_cart_items_with_count,cartProductList.count(),cartProductList.count())
                                visibility = if (cartProductList.isEmpty()) View.GONE else View.VISIBLE
                            }
                            mItemList.clear()
                            mItemDataList.mapIndexed { index,item ->
                                item.isChecked = cartProductList.firstOrNull {
                                    it.name == item.name
                                }?.isChecked ?: false
                                item.quantity = cartProductList.firstOrNull {
                                    it.name == item.name
                                }?.quantity ?: 1
                                mItemList.add(EMarketStoreItemDisplayer(index,item,::onClickCheckBox,::onClickCounter))
                            }
                            mAdapter.setData(mItemList)

                        }
                    }
                }

        }
    }

    override fun listeners() {
        binding.tvSelectAll.setOnClickListener{
            mItemList.clear()
            isSelectedAll = !isSelectedAll
            binding.tvSelectAll.text = if (isSelectedAll) getString(R.string.txt_unselect_all) else getString(R.string.txt_select_all)
            mItemDataList.mapIndexed { index,item ->
                item.isChecked = isSelectedAll
                mItemList.add(EMarketStoreItemDisplayer(index,item,::onClickCheckBox,::onClickCounter))
            }
            mAdapter.setData(mItemList)
        }
        binding.btnCart.setOnClickListener {
            if (isEditing){
                finish()
            }else{
                startActivity(EMarketPlaceOrderActivity.newIntent(this))
            }
        }
    }

    private fun onClickCheckBox(position:Int, isChecked:Boolean, data: EMarketShopProductListVO){
        mItemDataList.removeAt(position)
        mItemDataList.add(position,data)
        if (isChecked){
            mViewModel.addItemToCart(data)
        }else{
            mViewModel.removeItemFromCart(data)
        }
        mViewModel.setCardItemList(mItemDataList.filter { it.isChecked == true } as ArrayList<EMarketShopProductListVO>)
    }

    private fun onClickCounter(position:Int, data:EMarketShopProductListVO){
        mItemDataList.removeAt(position)
        mItemDataList.add(position,data)
        mItemList.clear()
        mItemDataList.mapIndexed { index,item ->
            mItemList.add(EMarketStoreItemDisplayer(index,item,::onClickCheckBox,::onClickCounter))
        }
        mAdapter.setData(mItemList)
//        mItemList.removeAt(position)
//        mItemList.add(position,EMarketStoreItemDisplayer(position,data,::onClickCheckBox,::onClickCounter))
//        mAdapter.addDataAtPosition(mItemList[position],position)
        mViewModel.setCardItemList(mItemDataList.filter { it.isChecked == true } as ArrayList<EMarketShopProductListVO>)
    }

}