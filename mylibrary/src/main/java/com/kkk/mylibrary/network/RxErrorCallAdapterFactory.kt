package com.kkk.mylibrary.network

import io.reactivex.subjects.PublishSubject
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class RxErrorCallAdapterFactory(
    private val mDelegate: CallAdapter.Factory,
    private val errorSubject: PublishSubject<Any>
) :
    CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *> {
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = getRawType(observableType)
        var isResponse = false
        if (rawObservableType == Response::class.java) {
            isResponse = true
        }
        return RxErrorCallAdapter(
            mDelegate.get(returnType, annotations, retrofit) as CallAdapter<Any, Any>,isResponse,errorSubject
        )
    }

}
