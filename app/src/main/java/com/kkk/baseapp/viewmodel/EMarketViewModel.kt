package com.kkk.baseapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kkk.baseapp.domain.repo.EMarketRepository
import com.kkk.baseapp.data.vos.EMarketShopProductListVO
import com.kkk.baseapp.di.hilt.IoDispatcher
import com.kkk.baseapp.network.networkrequest.EMarketPlaceOrderRequest
import com.kkk.baseapp.network.networkrequest.Product
import com.kkk.baseapp.network.networkresponse.emarket.EMarketShopResponse
import com.kkk.mylibrary.network.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EMarketViewModel @Inject constructor(
    private val eMarketRepo: EMarketRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): BaseViewModel() {

    private var _storeInfoLD:MutableLiveData<ResourceState<EMarketShopResponse>> = MutableLiveData(ResourceState.Loading)
    val storeInfoLD: LiveData<ResourceState<EMarketShopResponse>> = _storeInfoLD

    private var _storeProductListLD:MutableLiveData<ResourceState<List<EMarketShopProductListVO>>> = MutableLiveData(ResourceState.Loading)
    val storeProductListLD: LiveData<ResourceState<List<EMarketShopProductListVO>>> = _storeProductListLD

    private var _cartProductList:MutableList<EMarketShopProductListVO> = mutableListOf()
    private var _cartProductListLD:MutableLiveData<ResourceState<MutableList<EMarketShopProductListVO>>> = MutableLiveData(ResourceState.Loading)
    val cartProductListLD: LiveData<ResourceState<MutableList<EMarketShopProductListVO>>> = _cartProductListLD

    private var _makeOrderLD:MutableLiveData<ResourceState<Int>> = MutableLiveData(ResourceState.Loading)
    val makeOrderLD: LiveData<ResourceState<Int>> = _makeOrderLD

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
    fun makeOrder(itemList: MutableList<EMarketShopProductListVO>, deliverAddress: String) {
        _makeOrderLD.postValue(ResourceState.Loading)
        val request = EMarketPlaceOrderRequest(deliverAddress,itemList.map { Product(it.imageUrl,it.name,it.price) })
        viewModelScope.launch(dispatcher) {
            eMarketRepo.makeOrder(request)
                .collect{
                    if (it is ResourceState.Success){
                        _makeOrderLD.postValue(ResourceState.Success(200))
                    }else if (it is ResourceState.HttpError){
                        if (it.code == 900){
                            _makeOrderLD.postValue(ResourceState.Success(200))
                        }else{
                            _makeOrderLD.postValue(it.resultToResultWithoutSuccessData(it))
                        }
                }else{
                        _makeOrderLD.postValue(it.resultToResultWithoutSuccessData(it))
                    }
                }
        }
    }

    fun getCardItemList(){
        _cartProductListLD.postValue(ResourceState.Success(_cartProductList))
    }

    fun setCardItemList(data: ArrayList<EMarketShopProductListVO>) {
        _cartProductList = data
        _cartProductListLD.postValue(ResourceState.Success(_cartProductList))
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
                _cartProductList.add(itemIndex,it)
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