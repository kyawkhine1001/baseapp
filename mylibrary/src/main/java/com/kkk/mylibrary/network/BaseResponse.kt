package com.kkk.mylibrary.network

class BaseResponse<D> {
    val result: D? = null
    //    val errors: List<ErrorResponse>? = errors
    val message = ""
    val statusCode = 0
}