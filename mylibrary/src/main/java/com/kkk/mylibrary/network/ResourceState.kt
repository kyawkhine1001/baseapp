package com.kkk.mylibrary.network

sealed class ResourceState<out T>(open val successData: T?) {
    object Initial: ResourceState<Nothing>(null)
    object Loading: ResourceState<Nothing>(null)
    data class Success<out T>(override val successData: T) : ResourceState<T>(null)
    class SystemError(val code: Int? = null, val error: String? = null) :
        ResourceState<Nothing>(null)
    class HttpError(val code: Int? = null, val error: String? = null) :
        ResourceState<Nothing>(null)
    class Error(val code: Int? = null, val error: String? = null) :
        ResourceState<Nothing>(null)

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
