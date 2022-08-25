package com.kkk.baseapp.ui.adapter.displayer.emarket

import androidx.databinding.ViewDataBinding
import com.kkk.baseapp.R
import com.kkk.baseapp.data.vos.EMarketShopProductListVO
import com.kkk.baseapp.databinding.ListItemEmarketShopProductBinding
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopProductListResponseItem
import com.kkk.mylibrary.ui.adapter.displayer.ItemDisplayer
import com.kkk.mylibrary.ui.adapter.displayer.ViewType
import com.kkk.mylibrary.ui.custom_view.CustomCounterView
import com.kkk.mylibrary.utils.extensions.loadImageWithGlide

class EMarketStoreItemDisplayer(private val position:Int, private val data: EMarketShopProductListVO, private val onClickCheckBox:(Int, Boolean, EMarketShopProductListVO) -> Unit, private val onClickCounter:(Int, EMarketShopProductListVO) -> Unit) :ItemDisplayer {
    override fun getViewType(): ViewType  = ViewType(R.layout.list_item_emarket_shop_product)

    override fun bind(vb: ViewDataBinding) {
        val binding = vb as ListItemEmarketShopProductBinding
        binding.apply {
            ivStore.loadImageWithGlide(root.context,data.imageUrl)
            tvMenuName.text = data.name
            tvMenuPrice.text = "$ ${data.price}"
            cbMenu.isChecked = data.isChecked == true
            ccvMenu.setCurrentCount(data.quantity)
            ccvMenu.setOnClickListeners(object : CustomCounterView.CounterButtonListener {
                override fun onClickMinusButton() {
                    updateCounterValue(binding)
                }

                override fun onClickPlusButton() {
                    updateCounterValue(binding)
                }

            })
            cbMenu.setOnCheckedChangeListener { compoundButton, isChecked ->
                data.isChecked = isChecked
                onClickCheckBox(position,isChecked,data)
            }
            root.setOnClickListener {
                data.isChecked = cbMenu.isChecked
                cbMenu.toggle()
            }
        }
    }

    private fun updateCounterValue(binding:ListItemEmarketShopProductBinding){
        val updatedData = data
        val currentCount = binding.ccvMenu.getCurrentCount()
        updatedData.quantity = currentCount
        onClickCounter(position,updatedData)
    }
}