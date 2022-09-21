package com.kkk.baseapp.mock

import com.kkk.baseapp.domain.repo.EMarketRepository
import com.kkk.baseapp.mock.data.MockDataUtils
import com.kkk.baseapp.network.networkrequest.EMarketPlaceOrderRequest
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopProductListResponseItem
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopResponse
import com.kkk.mylibrary.network.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockEMarketRepository: EMarketRepository {
    override fun getStoreInfo(): Flow<ResourceState<EMarketShopResponse>> {
        return flow { ResourceState.Success(MockDataUtils.getStoreDetail()) }
    }

    override fun getStoreProductList(): Flow<ResourceState<List<EMarketShopProductListResponseItem>>> {
        TODO("Not yet implemented")
    }

    override fun makeOrder(request: EMarketPlaceOrderRequest): Flow<ResourceState<Void>> {
        TODO("Not yet implemented")
    }
}