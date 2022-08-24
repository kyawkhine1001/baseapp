package com.kkk.baseapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kkk.baseapp.data.repositories.EMarketRepository
import com.kkk.baseapp.data.vos.EMarketShopProductListVO
import com.kkk.baseapp.di.hilt.IoDispatcher
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopProductListResponse
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopProductListResponseItem
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopResponse
import com.kkk.mylibrary.network.ResourceState
import com.kkk.mylibrary.network.rx.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EMarketViewModel @Inject constructor(
    private val eMarketRepo: EMarketRepository,
    private val schedulers: SchedulerProvider,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): BaseViewModel() {

    private var _storeInfoLD:MutableLiveData<ResourceState<EMarketShopResponse>> = MutableLiveData(ResourceState.Loading)
    val storeInfoLD: LiveData<ResourceState<EMarketShopResponse>> = _storeInfoLD

    private var _storeProductListLD:MutableLiveData<ResourceState<List<EMarketShopProductListVO>>> = MutableLiveData(ResourceState.Loading)
    val storeProductListLD: LiveData<ResourceState<List<EMarketShopProductListVO>>> = _storeProductListLD

    private var _cartProductList:MutableList<EMarketShopProductListVO> = mutableListOf()
    private var _cartProductListLD:MutableLiveData<ResourceState<MutableList<EMarketShopProductListVO>>> = MutableLiveData(ResourceState.Loading)
    val cartProductListLD: LiveData<ResourceState<MutableList<EMarketShopProductListVO>>> = _cartProductListLD

    fun getAllStoreInfo(){
        getStoreInfo()
        getStoreProductList()
    }

    fun getStoreInfo(){
        _storeInfoLD.postValue(ResourceState.Loading)
        viewModelScope.launch(dispatcher) {
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
        viewModelScope.launch(dispatcher) {
            eMarketRepo.getStoreProductList()
                .collect{
                    if (it is ResourceState.Success){
                        _storeProductListLD.postValue(ResourceState.Success(it.successData.map { it.mappingToVO() }))
                    }else{
                        _storeProductListLD.postValue(it.resultToResultWithoutSuccessData(it))
                    }
                }
        }
    }
    fun makeOrder(){

    }

    fun addItemToCart(data: EMarketShopProductListVO) {
        val itemIndex = _cartProductList.indexOf(_cartProductList.firstOrNull { it.name == data.name })
        if (itemIndex == -1){
            _cartProductList.add(data)
        }else{
            val item = _cartProductList.getOrNull(itemIndex)
            item?.let {
                it.quantity += 1
                _cartProductList.removeAt(itemIndex)
                _cartProductList.add(itemIndex,item)
            }
        }
        _cartProductListLD.postValue(ResourceState.Success(_cartProductList))
    }

    fun removeItemFromCart(data: EMarketShopProductListVO) {
        val itemIndex = _cartProductList.indexOf(_cartProductList.firstOrNull { it.name == data.name })
        if (itemIndex != -1){
            _cartProductList.removeAt(itemIndex)
        }
        _cartProductListLD.postValue(ResourceState.Success(_cartProductList))
    }
}