package com.kkk.mylibrary.network

sealed class ResourceState<out T> {
    object Initial: ResourceState<Nothing>()
    object Loading: ResourceState<Nothing>()
    data class Success<out T>(val successData: T) : ResourceState<T>()
    class SystemError(val code: Int? = null, val error: String? = null) :
        ResourceState<Nothing>()
    class HttpError(val code: Int? = null, val error: String? = null) :
        ResourceState<Nothing>()
    class Error(val code: Int? = null, val error: String? = null) :
        ResourceState<Nothing>()

    fun <I,T> resultToResultWithoutSuccessData(data:ResourceState<I>):ResourceState<T>{
        var resourceState:ResourceState<T> = Loading
        when (data) {
            is Loading -> {
                resourceState = Loading
            }
            is Success -> {
            }
            is SystemError -> {
                resourceState = data
            }
            is HttpError -> {
                resourceState = data
            }
            is Error -> {
                resourceState = data
            }
            else -> {
            }
        }
        return resourceState

    }
}
