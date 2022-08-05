package com.kkk.baseapp.ui.adapter.displayer.emarket

import androidx.databinding.ViewDataBinding
import com.kkk.baseapp.R
import com.kkk.baseapp.databinding.ListItemEmarketShopProductBinding
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopProductListResponseItem
import com.kkk.mylibrary.ui.adapter.displayer.ItemDisplayer
import com.kkk.mylibrary.ui.adapter.displayer.ViewType
import com.kkk.mylibrary.ui.custom_view.CustomCounterView
import com.kkk.mylibrary.utils.extensions.loadImageWithGlide

class EMarketStoreItemDisplayer(private val position:Int, private val data: EMarketShopProductListResponseItem,private val onClickCheckBox:(Int,EMarketShopProductListResponseItem) -> Unit,private val onClickItem:(Int,EMarketShopProductListResponseItem) -> Unit) :ItemDisplayer {
    override fun getViewType(): ViewType  = ViewType(R.layout.list_item_emarket_shop_product)

    override fun bind(vb: ViewDataBinding) {
        val binding = vb as ListItemEmarketShopProductBinding
        binding.apply {
            ivStore.loadImageWithGlide(root.context,data.imageUrl)
            tvMenuName.text = data.name
            tvMenuPrice.text = "$ ${data.price}"
            cbMenu.isChecked = data.isChecked == true
            ccvMenu.setOnClickListeners(object : CustomCounterView.CounterButtonListener {
                override fun onClickMinusButton() {

                }

                override fun onClickPlusButton() {
                }

            })
            cbMenu.setOnCheckedChangeListener { compoundButton, isChecked ->
                onClickCheckBox(position,data)
            }
            root.setOnClickListener { onClickItem(position,data) }
        }
    }
}