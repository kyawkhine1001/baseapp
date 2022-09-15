package com.kkk.mylibrary.utils.extensions.network

import com.kkk.mylibrary.network.ResourceState
import com.kkk.mylibrary.utils.constants.AppConstants.SOMETHING_WENT_WRONG_CODE
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import retrofit2.HttpException
import java.io.IOException
import java.net.ProtocolException
import java.net.UnknownHostException

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): ResourceState<T> {
    return withContext(dispatcher) {
        try {
            withTimeout(60000) {
                ResourceState.Success(apiCall.invoke())
            }
        } catch (throwable: Throwable) {
            when (throwable) {
                is ProtocolException ->  ResourceState.Error(SOMETHING_WENT_WRONG_CODE, "Something Went Wrong")
                is IOException ->  ResourceState.Error(SOMETHING_WENT_WRONG_CODE,"Something Went Wrong")
                is UnknownHostException ->  ResourceState.Error(SOMETHING_WENT_WRONG_CODE,"Something Went Wrong")
                is TimeoutCancellationException ->  ResourceState.Error(SOMETHING_WENT_WRONG_CODE,"Something Went Wrong")
                is HttpException -> {
                    val code = throwable.code()
//                    val errorResponse = convertErrorBody(throwable)
                    val errorMsg = "Something Went Wrong"
                    if (code == 401){
                        ResourceState.HttpError(
                            code,
                            errorMsg
                        )
                    }else {
                        ResourceState.Error(
                            code,
                            errorMsg
                        )
                    }
                }
                else -> {
                    ResourceState.Error(SOMETHING_WENT_WRONG_CODE,"Something Went Wrong")
                }
            }
        }
    }
}
