package com.kkk.mylibrary.utils.extensions.network

import com.kkk.mylibrary.network.BaseResponse
import retrofit2.Call
import retrofit2.HttpException
import retrofit2.Response
import java.lang.Exception
import java.net.UnknownHostException

const val UNKNOWN_ERROR = "Unknown error"
const val SUCCESS_WITH_NULL_ERROR = "Success with null error"
const val UNKNOWN_ERROR_MESSAGE = "Unknown error message"
const val NETWORK_ERROR = "Network Error"

fun <T> Call<T>.executeOrThrow(): T? {
    var response: Response<T>? = null
    try {
        response = this.execute()
        if (response.isSuccessful.not()) {
            response.errorBody()?.let {
                val errCode = response.code()
                if (errCode in 400..599)
                    throw HttpException(
                        Response.error<BaseResponse<T>>(
                            errCode,
                            it
                        )
                    )
            }

        }
    } catch (httpException: HttpException) {
        response?.errorBody()?.let {
            throw HttpException(
                Response.error<BaseResponse<T>>(
                    httpException.code(),
                    it
                )
            )
        }
        httpException.printStackTrace()
        throw httpException
    } catch (unknownHostException: UnknownHostException) {
        unknownHostException.printStackTrace()
        throw unknownHostException
    } catch (e: Exception) {
        e.printStackTrace()
        throw e
    }
    return response.body()

}