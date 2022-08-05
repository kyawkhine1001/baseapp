package com.kkk.baseapp.data.repositories

import android.content.Context
import com.kkk.baseapp.data.db.MyDatabase
import com.kkk.baseapp.network.ApiService
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopProductListResponse
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopResponse
import com.kkk.mylibrary.network.ResourceState
import com.kkk.mylibrary.utils.network.HandleResponseUtil
import kotlinx.coroutines.flow.Flow

class EMarketRepositoryImpl(
    private val context: Context,
    private val mApiService: ApiService,
    private val database: MyDatabase
):EMarketRepository {

    override fun getStoreInfo(): Flow<ResourceState<EMarketShopResponse>>  = HandleResponseUtil.doNetworkCall(context,mApiService.getStoreInfo())

    override fun getStoreProductList(): Flow<ResourceState<EMarketShopProductListResponse>>  = HandleResponseUtil.doNetworkCall(context,mApiService.getStoreProductList())

    override fun makeOrder(): Flow<ResourceState<Int>>  = HandleResponseUtil.doNetworkCall(context,mApiService.makeOrder())
}