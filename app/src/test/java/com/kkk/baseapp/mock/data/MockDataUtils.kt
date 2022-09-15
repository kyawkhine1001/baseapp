package com.kkk.baseapp.mock.data

import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopResponse

object MockDataUtils {
    fun getStoreDetail():EMarketShopResponse{
        return EMarketShopResponse("10","KKK Test","9",4.5)
    }
}