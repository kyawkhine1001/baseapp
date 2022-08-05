package com.kkk.baseapp.ui.activity.emarket

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.GridLayoutManager
import com.kkk.baseapp.R
import com.kkk.baseapp.databinding.ActivityEmarketHomeBinding
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopProductListResponseItem
import com.kkk.baseapp.ui.adapter.displayer.TitleDisplayer
import com.kkk.baseapp.ui.adapter.displayer.emarket.EMarketStoreItemDisplayer
import com.kkk.baseapp.viewmodel.EMarketViewModel
import com.kkk.mylibrary.network.ResourceState
import com.kkk.mylibrary.ui.activity.BaseViewBindingActivity
import com.kkk.mylibrary.ui.adapter.DelegateAdapter
import com.kkk.mylibrary.ui.adapter.displayer.ItemDisplayer
import com.kkk.mylibrary.utils.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel

@AndroidEntryPoint
class EMarketHomeActivity : BaseViewBindingActivity<ActivityEmarketHomeBinding>() {

    companion object{
        private const val IE_NAME = "IE_NAME"
        fun newIntent(context: Context, name:String): Intent {
            val intent = Intent(context, EMarketHomeActivity::class.java)
            intent.putExtra(IE_NAME,name)
            return intent
        }
    }

    private var isShow = true
    private var scrollRange = -1

    private var mItemDataList = mutableListOf<EMarketShopProductListResponseItem>()
    private var mItemList = mutableListOf<ItemDisplayer>()
    private val mAdapter: DelegateAdapter = DelegateAdapter()
    private var isSelectedAll:Boolean = false

//    private val mViewModel: EMarketViewModel by viewModel()
    private val mViewModel by lazy {
        ViewModelProvider(this)[EMarketViewModel::class.java]
}

    override val bindingInflater: (LayoutInflater) -> ActivityEmarketHomeBinding
        get() = ActivityEmarketHomeBinding::inflate

    override fun setup() {
        setupUI()
        mViewModel.getAllStoreInfo()
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
                        tvStoreOpeningTime.text = "Opening time : ${data.openingTime} to ${data.closingTime}"
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
                        mItemDataList = it.successData
                        mItemDataList.mapIndexed { index,item ->
                            mItemList.add(EMarketStoreItemDisplayer(index,item,::onClickCheckBox,::onClickMenuItem))
                        }
                        mAdapter.setData(mItemList)
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
    }

    override fun listeners() {
        binding.tvSelectAll.setOnClickListener{
            mItemList.clear()
            isSelectedAll = !isSelectedAll
            binding.tvSelectAll.text = if (isSelectedAll) getString(R.string.txt_unselect_all) else getString(R.string.txt_select_all)
            mItemDataList.mapIndexed { index,item ->
                item.isChecked = isSelectedAll
                mItemList.add(EMarketStoreItemDisplayer(index,item,::onClickCheckBox,::onClickMenuItem))
            }
            mAdapter.setData(mItemList)
        }
    }

    private fun onClickCheckBox(position:Int,data: EMarketShopProductListResponseItem){

    }

    private fun onClickMenuItem(position:Int,data:EMarketShopProductListResponseItem){

    }

}