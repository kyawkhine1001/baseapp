package com.kkk.baseapp.ui.activity.emarket

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.GridLayoutManager
import com.kkk.baseapp.R
import com.kkk.baseapp.data.vos.EMarketShopProductListVO
import com.kkk.baseapp.databinding.ActivityEmarketPlaceOrderBinding
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopProductListResponse
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopProductListResponseItem
import com.kkk.baseapp.ui.adapter.displayer.emarket.EMarketStoreCartItemDisplayer
import com.kkk.mylibrary.ui.activity.BaseViewBindingActivity
import com.kkk.mylibrary.ui.adapter.DelegateAdapter
import com.kkk.mylibrary.ui.adapter.displayer.ItemDisplayer

class EMarketPlaceOrderActivity : BaseViewBindingActivity<ActivityEmarketPlaceOrderBinding>() {

    private var mItemDataList = mutableListOf<EMarketShopProductListVO>()
    private var mItemList = mutableListOf<ItemDisplayer>()
    private val mAdapter: DelegateAdapter = DelegateAdapter()

    companion object{
        private const val IE_DATA = "IE_DATA"
        fun newIntent(context: Context,data: EMarketShopProductListResponse):Intent{
            val intent = Intent(context,EMarketPlaceOrderActivity::class.java)
            intent.putExtra(IE_DATA,data)
            return intent
        }
    }

    override val bindingInflater: (LayoutInflater) -> ActivityEmarketPlaceOrderBinding
        get() = ActivityEmarketPlaceOrderBinding::inflate

    override fun setup() {
        setupUI()
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
        val data: EMarketShopProductListResponse? = intent.getSerializableExtra(IE_DATA) as? EMarketShopProductListResponse
        data?.data?.mapIndexed {index,item ->
            mItemList.add(EMarketStoreCartItemDisplayer(index,item,{a,aa ->

            },{a,aa ->

            }))
        }
//        for (i in 0..10){
//            mItemList.add(EMarketStoreCartItemDisplayer(i,"",{a,aa ->
//
//            },{a,aa ->
//
//            }))
//        }
        mAdapter.setData(mItemList)
    }

    override fun observers() {

    }

    override fun listeners() {
        binding.btnCart.setOnClickListener {
            finish()
            startActivity(EMarketPlaceOrderSuccessActivity.newIntent(this))
        }
    }
}