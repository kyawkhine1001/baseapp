package com.kkk.baseapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kkk.baseapp.data.repositories.EMarketRepository
import com.kkk.baseapp.data.repositories.MainRepository
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopProductListResponse
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopResponse
import com.kkk.mylibrary.network.ResourceState
import com.kkk.mylibrary.network.rx.SchedulerProvider
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EMarketViewModel(
    private val eMarketRepo: EMarketRepository,
    private val schedulers: SchedulerProvider
): BaseViewModel() {

    private var _storeInfoLD:MutableLiveData<ResourceState<EMarketShopResponse>> = MutableLiveData(ResourceState.Loading)
    val storeInfoLD: LiveData<ResourceState<EMarketShopResponse>> = _storeInfoLD

    private var _storeProductListLD:MutableLiveData<ResourceState<EMarketShopProductListResponse>> = MutableLiveData(ResourceState.Loading)
    val storeProductListLD: LiveData<ResourceState<EMarketShopProductListResponse>> = _storeProductListLD

    fun getAllStoreInfo(){
        getStoreInfo()
        getStoreProductList()
    }
    fun getStoreInfo(){
        _storeInfoLD.postValue(ResourceState.Loading)
        viewModelScope.launch {
            eMarketRepo.getStoreInfo()
                .collect {
                    if (it is ResourceState.Success){
                        _storeInfoLD.postValue(it)
                    }else{
                        _storeInfoLD.postValue(it.resultToResultWithoutSuccessData(it))
                    }
                }
        }
    }

    fun getStoreProductList(){
        _storeProductListLD.postValue(ResourceState.Loading)
        viewModelScope.launch {
            eMarketRepo.getStoreProductList()
                .collect{
                    if (it is ResourceState.Success){
                        _storeProductListLD.postValue(it)
                    }else{
                        _storeProductListLD.postValue(it.resultToResultWithoutSuccessData(it))
                    }
                }
        }
    }
    fun makeOrder(){

    }
}