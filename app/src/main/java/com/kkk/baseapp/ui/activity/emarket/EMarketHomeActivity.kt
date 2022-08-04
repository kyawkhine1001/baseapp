package com.kkk.baseapp.ui.activity.emarket

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.appbar.AppBarLayout
import com.kkk.baseapp.R
import com.kkk.baseapp.databinding.ActivityEmarketHomeBinding
import com.kkk.baseapp.ui.activity.TravelActivity
import com.kkk.baseapp.ui.adapter.displayer.TitleDisplayer
import com.kkk.baseapp.viewmodel.EMarketViewModel
import com.kkk.baseapp.viewmodel.MainViewModel
import com.kkk.mylibrary.network.ResourceState
import com.kkk.mylibrary.ui.activity.BaseViewBindingActivity
import com.kkk.mylibrary.ui.adapter.DelegateAdapter
import com.kkk.mylibrary.ui.adapter.displayer.ItemDisplayer
import com.kkk.mylibrary.utils.extensions.showToast
import org.koin.androidx.viewmodel.ext.android.viewModel

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

    private var mItemList = mutableListOf<ItemDisplayer>()
    private val mAdapter: DelegateAdapter = DelegateAdapter()

    private val mViewModel: EMarketViewModel by viewModel()

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
//        mItemList.add(TitleDisplayer("Hello"))
//        mItemList.add(TitleDisplayer("Hello"))
//        mItemList.add(TitleDisplayer("Hello"))
//        mItemList.add(TitleDisplayer("Hello"))
//        mItemList.add(TitleDisplayer("Hello"))
//        mItemList.add(TitleDisplayer("Hello"))
//        mItemList.add(TitleDisplayer("Hello"))
//        mItemList.add(TitleDisplayer("Hello"))
//        mItemList.add(TitleDisplayer("Hello"))
//        mItemList.add(TitleDisplayer("Hello"))
//        mItemList.add(TitleDisplayer("Hello"))
//        mItemList.add(TitleDisplayer("Hello"))
//        mItemList.add(TitleDisplayer("Hello"))
//        mItemList.add(TitleDisplayer("Hello"))
//        mItemList.add(TitleDisplayer("Hello"))
//        mItemList.add(TitleDisplayer("Hello"))
//        mItemList.add(TitleDisplayer("Hello"))
//        mItemList.add(TitleDisplayer("Hello"))
//        mItemList.add(TitleDisplayer("Hello"))
//        mItemList.add(TitleDisplayer("Hello"))
        mAdapter.setData(mItemList)
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
                        val data = it.successData
                        data.map {
                            mItemList.add(TitleDisplayer(it.name))
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
    }

}