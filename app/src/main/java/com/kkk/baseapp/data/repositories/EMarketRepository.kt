package com.kkk.baseapp.data.repositories

import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopProductListResponse
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopProductListResponseItem
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopResponse
import com.kkk.mylibrary.network.ResourceState
import kotlinx.coroutines.flow.Flow

interface EMarketRepository {
    fun getStoreInfo(): Flow<ResourceState<EMarketShopResponse>>
    fun getStoreProductList(): Flow<ResourceState<List<EMarketShopProductListResponseItem>>>
    fun makeOrder(): Flow<ResourceState<Int>>
}