package com.kkk.baseapp.ui.activity.emarket

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kkk.baseapp.R
import com.kkk.baseapp.data.vos.EMarketShopProductListVO
import com.kkk.baseapp.databinding.ActivityEmarketPlaceOrderBinding
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopProductListResponse
import com.kkk.baseapp.ui.adapter.displayer.emarket.EMarketStoreCartItemDisplayer
import com.kkk.baseapp.util.PreferenceConstants
import com.kkk.baseapp.viewmodel.EMarketViewModel
import com.kkk.mylibrary.network.ResourceState
import com.kkk.mylibrary.ui.activity.BaseViewBindingActivity
import com.kkk.mylibrary.ui.adapter.DelegateAdapter
import com.kkk.mylibrary.ui.adapter.displayer.ItemDisplayer
import com.kkk.mylibrary.utils.extensions.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.reflect.Type

@AndroidEntryPoint
class EMarketPlaceOrderActivity : BaseViewBindingActivity<ActivityEmarketPlaceOrderBinding>() {

    private var cartCache: ArrayList<EMarketShopProductListVO> = arrayListOf()
    private var mItemDataList = mutableListOf<EMarketShopProductListVO>()
    private var mItemList = mutableListOf<ItemDisplayer>()
    private val mAdapter: DelegateAdapter = DelegateAdapter()

        private val mViewModel by lazy {
        ViewModelProvider(this)[EMarketViewModel::class.java]
    }

    companion object{
        fun newIntent(context: Context): Intent {
            return Intent(context, EMarketPlaceOrderActivity::class.java)
        }
    }

    override val bindingInflater: (LayoutInflater) -> ActivityEmarketPlaceOrderBinding
        get() = ActivityEmarketPlaceOrderBinding::inflate

    override fun setup() {
        setupUI()
        mViewModel.getCardItemList()
        checkPlaceOrderButtonEnable()
    }

    private fun setupUI(){
        setupToolbar()
        setupRecyclerView()
    }

    private fun setupToolbar(){
        binding.apply {
            tbToolbar.tvToolbarTitle.text = getString(R.string.txt_place_order)
            tbToolbar.tbBackArrow.setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun setupRecyclerView(){
        binding.rvCartItem.apply{
            layoutManager = GridLayoutManager(context, 1)
            adapter = mAdapter
        }
    }

    override fun observers() {
        lifecycleScope.launch {
            getStringData(PreferenceConstants.DATASTORE_E_MARKET_ORDER_ITEM_CART)
                .collectLatest { cartItemListString ->
                    cartItemListString?.let {
                        val listType: Type =
                            object : TypeToken<ArrayList<EMarketShopProductListVO?>?>() {}.type
                        cartCache = Gson().fromJson(it, listType)
                        binding.apply {
                            mItemList.clear()
                            mItemDataList = cartCache
                            var totalAmount:Int = 0
                            mItemDataList.mapIndexed {index,item ->
                                totalAmount += item.quantity*item.price
                                mItemList.add(EMarketStoreCartItemDisplayer(index,item,
                                    {position,cartItem ->

                                    },{position,cartItem ->
//                                        mViewModel.removeItemFromCart(cartItem)
                                        cartCache.removeAt(position)
                                        lifecycleScope.launch {
                                            setStringData(PreferenceConstants.DATASTORE_E_MARKET_ORDER_ITEM_CART,Gson().toJson(cartCache))
                                        }
                                        checkPlaceOrderButtonEnable()
                                    }))
                            }
                            binding.tvTotalAmount.text = getString(R.string.txt_dollar,totalAmount)
                            mAdapter.setData(mItemList)
                            checkPlaceOrderButtonEnable()
                        }
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
        mViewModel.makeOrderLD.observe(this){
            binding.loadingView.root.visibility =
                if (it is ResourceState.Loading) View.VISIBLE else View.GONE
            when(it){
                is ResourceState.Loading, ResourceState.Initial -> {

                }
                is ResourceState.Success -> {
                    lifecycleScope.launch {
                        deleteStringData(PreferenceConstants.DATASTORE_E_MARKET_ORDER_ITEM_CART)
                    }
                    finish()
                    startActivity(EMarketPlaceOrderSuccessActivity.newIntent(this))
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
    }

    override fun listeners() {
        binding.edtDeliverAddress.onTextChange {
            checkPlaceOrderButtonEnable()
        }
        binding.tvAddMore.setOnClickListener {
            startActivity(EMarketHomeActivity.newIntent(this, isEditing = true))
        }
        binding.btnPlaceOrder.setOnClickListener {
            val deliverAddress = binding.edtDeliverAddress.text.toString()
            mViewModel.makeOrder(mItemDataList,deliverAddress)
        }
    }

    private fun checkPlaceOrderButtonEnable(){
        binding.btnPlaceOrder.isEnabled = binding.edtDeliverAddress.text?.isNotBlank() == true && mItemDataList.isNotEmpty()
    }
}