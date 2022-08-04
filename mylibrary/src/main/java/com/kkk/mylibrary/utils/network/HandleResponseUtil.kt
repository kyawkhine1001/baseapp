package com.kkk.mylibrary.utils.network

import android.content.Context
import com.kkk.mylibrary.R
import com.kkk.mylibrary.network.BaseResponse
import com.kkk.mylibrary.network.ResourceState
import com.kkk.mylibrary.utils.constants.AppConstants
import com.kkk.mylibrary.utils.extensions.network.SUCCESS_WITH_NULL_ERROR
import com.kkk.mylibrary.utils.extensions.network.UNKNOWN_ERROR_MESSAGE
import com.kkk.mylibrary.utils.extensions.network.executeOrThrow
import com.kkk.mylibrary.utils.extensions.network.safeApiCall
//import com.tamron.akoneyazay.R
//import com.tamron.akoneyazay.data.remote.ResourceState
//import com.tamron.akoneyazay.data.remote.response.BaseResponse
//import com.tamron.akoneyazay.util.constants.AppConstant
//import com.tamron.akoneyazay.util.extensions.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Call

object HandleResponseUtil {

    fun <T : Any> doNetworkCall(context: Context, call: Call<BaseResponse<T>>?): Flow<ResourceState<BaseResponse<T>>> {
        return flow {
            if (ConnectionCheckUtil.isOnline(context)) {
                val apiResult = safeApiCall(Dispatchers.IO) {
                    call?.executeOrThrow()
                }
                emit(handleResponse(context,apiResult))
            } else {
                emit(
                    ResourceState.HttpError(
                        AppConstants.NO_CONNECTION_CODE,
                        context.getString(R.string.txt_no_network_connectivity)
                    )
                )
            }
        }.catch { error ->
            error.printStackTrace()
            emit(
                ResourceState.HttpError(
                    AppConstants.SOMETHING_WENT_WRONG_CODE,
                    error.message ?: UNKNOWN_ERROR_MESSAGE
                )
            )
        }
    }

    fun <T : Any> doNetworkCallNormal(context: Context, call: Call<T>?): Flow<ResourceState<T>> {
        return flow {
            if (ConnectionCheckUtil.isOnline(context)) {
                val apiResult = safeApiCall(Dispatchers.IO) {
                    call?.executeOrThrow()
                }
                emit(handleResponseNormal(context,apiResult))
            } else {
                emit(
                    ResourceState.HttpError(
                        AppConstants.NO_CONNECTION_CODE,
                        context.getString(R.string.txt_no_network_connectivity)
                    )
                )
            }
        }.catch { error ->
            error.printStackTrace()
            emit(
                ResourceState.HttpError(
                    AppConstants.SOMETHING_WENT_WRONG_CODE,
                    error.message ?: UNKNOWN_ERROR_MESSAGE
                )
            )
        }
    }

    fun <T : Any> doNetworkCallWithoutBaseResponse(context: Context, call: Call<BaseResponse<T>>?): Flow<ResourceState<T>> {
        return flow {
            if (ConnectionCheckUtil.isOnline(context)) {
                val apiResult = safeApiCall(Dispatchers.IO) {
                    call?.executeOrThrow()
                }
                emit(handleResponseWithoutBaseResponse(context,apiResult))
            } else {
                emit(
                    ResourceState.HttpError(
                        AppConstants.NO_CONNECTION_CODE,
                        context.getString(R.string.txt_no_network_connectivity)
                    )
                )
            }
        }.catch { error ->
            error.printStackTrace()
            emit(
                ResourceState.HttpError(
                    AppConstants.SOMETHING_WENT_WRONG_CODE,
                    error.message ?: UNKNOWN_ERROR_MESSAGE
                )
            )
        }
    }


    private fun <T : Any> handleResponse(context: Context,apiResult: ResourceState<BaseResponse<T>?>): ResourceState<BaseResponse<T>> {
        when (apiResult) {
            is ResourceState.Success -> {
                apiResult.successData?.let {

                    return if (it.statusCode in 200..299) {
                        (ResourceState.Success(it))
                    }else {
                        (ResourceState.SystemError(it.statusCode, it.message))
                    }

                } ?: return (ResourceState.HttpError(900, SUCCESS_WITH_NULL_ERROR))
            }
            is ResourceState.HttpError -> {
                return if (apiResult.code == 401){
//                    context.logout()
                    (ResourceState.HttpError(apiResult.code, "You need to login again!"))
                }else{
                    (ResourceState.HttpError(
                        apiResult.code,
                        apiResult.error
                    )
                            )
                }
            }
            is ResourceState.Error -> {
                return ResourceState.Error(
                    apiResult.code,
                    apiResult.error
                )
            }
            else -> {
                return ResourceState.HttpError(
                    901,
                    "Unknown Error"
                )
            }
        }
    }

    private fun <T : Any> handleResponseNormal(context: Context,apiResult: ResourceState<T?>): ResourceState<T> {
        when (apiResult) {
            is ResourceState.Success -> {
                apiResult.successData?.let {
                    return ResourceState.Success(it)
                } ?: return (ResourceState.HttpError(900, SUCCESS_WITH_NULL_ERROR))
            }
            is ResourceState.HttpError -> {
                return if (apiResult.code == 401){
//                    context.logout()
                    (ResourceState.HttpError(apiResult.code, "You need to login again!"))
                }else{
                    (ResourceState.HttpError(
                        apiResult.code,
                        apiResult.error
                    )
                            )
                }
            }
            is ResourceState.Error -> {
                return ResourceState.Error(
                    apiResult.code,
                    apiResult.error
                )
            }
            else -> {
                return ResourceState.HttpError(
                    901,
                    "Unknown Error"
                )
            }
        }
    }

    private fun <T : Any> handleResponseWithoutBaseResponse(context: Context,apiResult: ResourceState<BaseResponse<T>?>): ResourceState<T> {
        when (apiResult) {
            is ResourceState.Success -> {
                val successData = apiResult.successData
                return if(successData != null){
                    if (successData.statusCode in 200..299) {
                        if (successData.result != null){
                            (ResourceState.Success(successData.result))
                        }else{
                            (ResourceState.HttpError(900, SUCCESS_WITH_NULL_ERROR))
                        }
                    }else {
                        (ResourceState.SystemError(successData.statusCode, successData.message))
                    }
                }else{
                    (ResourceState.HttpError(900, SUCCESS_WITH_NULL_ERROR))
                }
            }
            is ResourceState.HttpError -> {
                return if (apiResult.code == 401){
//                    context.logout()
                    (ResourceState.HttpError(apiResult.code, "You need to login again!"))
                }else{
                    (ResourceState.HttpError(
                        apiResult.code,
                        apiResult.error
                    )
                            )
                }
            }
            is ResourceState.Error -> {
                return ResourceState.Error(
                    apiResult.code,
                    apiResult.error
                )
            }
            else -> {
                return ResourceState.HttpError(
                    901,
                    "Unknown Error"
                )
            }
        }
    }

}