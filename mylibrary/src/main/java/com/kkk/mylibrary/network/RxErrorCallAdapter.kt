package com.kkk.mylibrary.network

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import retrofit2.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class RxErrorCallAdapter<R>(
    private val mDelegate: CallAdapter<R, Any>,
    private val isResponse: Boolean,
    private val errorSubject: PublishSubject<Any>
) : CallAdapter<R, Any> {

    override fun responseType(): Type {
        return mDelegate.responseType()
    }

    override fun adapt(call: Call<R>): Any {

        if (isResponse) {
            val observable = mDelegate.adapt(call) as Observable<Response<Any>>
            return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    if (!it.isSuccessful) {
                        it.errorBody()?.let {
                            sendErrorEvent(it.string())
                        }
                    }
                }
                .doOnError {
                }
                .doOnComplete {
                }

        } else {
            val observable = mDelegate.adapt(call) as Observable<Any>
            return observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                }
                .doOnError { e ->
//                    sendErrorEvent(e)
//                    if (e is HttpException) {
//                        e.response().errorBody()?.let {
//                            sendErrorEvent(it.string())
//                        }
//                    }

                    when (e) {
                        is java.net.ConnectException -> {
                            sendErrorEvent(NetworkResponseType.NoNetworkConnection)
                        }
                        is java.net.SocketTimeoutException -> {
                            sendErrorEvent(NetworkResponseType.ConnectionTimeOut)
                        }
                        else -> {
//                            if (e is HttpException) {
//                                when (e.response().code()) {
//                                    401 -> TokenExpired()
//                                    501 -> ForceTokenExpired()
//                                    403 -> Unauthorised()
//                                    406 -> PasswordExpired()
//                                }
//                            }
                        }
                    }

                }
                .doOnComplete {
                }
        }
    }

    fun sendErrorEvent(body: Any) {
            errorSubject.onNext(body)
    }
}
