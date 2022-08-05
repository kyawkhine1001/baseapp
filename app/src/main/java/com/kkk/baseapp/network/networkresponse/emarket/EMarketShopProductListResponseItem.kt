package com.kkk.baseapp.network.networkresponse.emarket

data class EMarketShopProductListResponseItem(
    val imageUrl: String,
    val name: String,
    val price: Int,
    var isChecked:Boolean? = false
)