package com.kkk.baseapp.ui.activity.emarket

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.kkk.baseapp.R
import com.kkk.baseapp.data.vos.EMarketShopProductListVO
import com.kkk.baseapp.databinding.ActivityEmarketPlaceOrderBinding
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopProductListResponse
import com.kkk.baseapp.ui.adapter.displayer.emarket.EMarketStoreCartItemDisplayer
import com.kkk.baseapp.viewmodel.EMarketViewModel
import com.kkk.mylibrary.network.ResourceState
import com.kkk.mylibrary.ui.activity.BaseViewBindingActivity
import com.kkk.mylibrary.ui.adapter.DelegateAdapter
import com.kkk.mylibrary.ui.adapter.displayer.ItemDisplayer
import com.kkk.mylibrary.utils.extensions.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EMarketPlaceOrderActivity : BaseViewBindingActivity<ActivityEmarketPlaceOrderBinding>() {

    private var mItemDataList = mutableListOf<EMarketShopProductListVO>()
    private var mItemList = mutableListOf<ItemDisplayer>()
    private val mAdapter: DelegateAdapter = DelegateAdapter()

        private val mViewModel by lazy {
        ViewModelProvider(this)[EMarketViewModel::class.java]
    }

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
        mViewModel.getCardItemList()
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
        data?.data?.let { mViewModel.setCardItemList(it) }
    }

    override fun observers() {
        mViewModel.cartProductListLD.observe(this){
            binding.loadingView.root.visibility =
                if (it is ResourceState.Loading) View.VISIBLE else View.GONE
            when(it){
                is ResourceState.Loading, ResourceState.Initial -> {

                }
                is ResourceState.Success -> {
                    binding.apply {
                        mItemList.clear()
                        mItemDataList = it.successData
                        var totalAmount:Int = 0
                        mItemDataList.mapIndexed {index,item ->
                            totalAmount += item.quantity*item.price
                            mItemList.add(EMarketStoreCartItemDisplayer(index,item,
                                {position,cartItem ->

                            },{position,cartItem ->
                                mViewModel.removeItemFromCart(cartItem)
                                showToast("Clicked on delete button")
                            }))
                        }
                        binding.tvTotalAmount.text = getString(R.string.txt_dollar,totalAmount)
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
        mViewModel.makeOrderLD.observe(this){
            binding.loadingView.root.visibility =
                if (it is ResourceState.Loading) View.VISIBLE else View.GONE
            when(it){
                is ResourceState.Loading, ResourceState.Initial -> {

                }
                is ResourceState.Success -> {
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
        binding.btnCart.setOnClickListener {
            val deliverAddress = binding.edtDeliverAddress.text.toString()
            mViewModel.makeOrder(mItemDataList,deliverAddress)
        }
    }

    fun checkPla
}