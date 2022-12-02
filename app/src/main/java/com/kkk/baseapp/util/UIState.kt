package com.kkk.baseapp.util

import com.kkk.mylibrary.network.ResourceState

sealed class UIState<out T>(open val successData:T?) {
    object Initial: UIState<Nothing>(null)
    object Loading: UIState<Nothing>(null)
    class Success<out T>(override val successData:T): UIState<T>(null)
    class Error(val code:Int? = null,val message: String?= null): UIState<Nothing>(null)
}
inline fun<reified T> ResourceState<T>.toUIState():UIState<T>{
    return when (this){
        is ResourceState.Loading -> UIState.Loading
        is ResourceState.Success -> UIState.Success(successData)
        is ResourceState.Error -> UIState.Error(code,message = error)
        else -> {UIState.Initial}
    }
}

inline fun<reified T> ResourceState<T>.toUIStateWithoutSuccess():UIState<Nothing> {
    return when (this) {
        is ResourceState.Loading -> UIState.Loading
        is ResourceState.Error -> UIState.Error(code, message = error)
        else -> {
            UIState.Initial
        }
    }
}