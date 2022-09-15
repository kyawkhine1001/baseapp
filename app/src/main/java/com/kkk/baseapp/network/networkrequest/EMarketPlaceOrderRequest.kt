package com.kkk.baseapp.network.networkrequest

data class EMarketPlaceOrderRequest(
    val delivery_address: String,
    val products: List<Product>
)