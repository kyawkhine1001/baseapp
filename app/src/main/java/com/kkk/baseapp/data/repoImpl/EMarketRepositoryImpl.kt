package com.kkk.baseapp.data.repoImpl

import android.content.Context
import com.kkk.baseapp.data.db.MyDatabase
import com.kkk.baseapp.domain.repo.EMarketRepository
import com.kkk.baseapp.network.ApiService
import com.kkk.baseapp.network.networkrequest.EMarketPlaceOrderRequest
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopProductListResponseItem
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopResponse
import com.kkk.mylibrary.network.ResourceState
import com.kkk.mylibrary.utils.network.HandleResponseUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EMarketRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context,
    private val mApiService: ApiService,
    private val database: MyDatabase
): EMarketRepository {

    override fun getStoreInfo(): Flow<ResourceState<EMarketShopResponse>>  = HandleResponseUtil.doNetworkCall(context,mApiService.getStoreInfo())

    override fun getStoreProductList(): Flow<ResourceState<List<EMarketShopProductListResponseItem>>>  = HandleResponseUtil.doNetworkCall(context,mApiService.getStoreProductList())

    override fun makeOrder(request: EMarketPlaceOrderRequest): Flow<ResourceState<Void>>  = HandleResponseUtil.doNetworkCall(context,mApiService.makeOrder(request))
}