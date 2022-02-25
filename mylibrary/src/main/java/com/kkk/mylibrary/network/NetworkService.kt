package com.kkk.mylibrary.network

import android.content.Context
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.kkk.mylibrary.BuildConfig
import io.reactivex.subjects.PublishSubject
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object API{
    val _errorSubject: PublishSubject<Any> = PublishSubject.create<Any>()
    val errorSubject = _errorSubject.hide()


    fun getCache(context: Context): Cache? {
        val DISK_CACHE_SIZE = 1 // 1 MB
        val cacheDir = File(context.cacheDir, "cache")
        return Cache(cacheDir, DISK_CACHE_SIZE.toLong())
    }

    fun getCertificatePinner(url:String,key:String):CertificatePinner{
        return CertificatePinner.Builder()
            .add(url, key)
            .build()
    }
}
inline fun <reified T> createWebService(url: String,_errorSubject:PublishSubject<Any>,headerInterceptor:Interceptor,certificatePinner:CertificatePinner?=null,getCache:Cache?=null): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(createOkHttpClient(_errorSubject,headerInterceptor,certificatePinner,getCache))
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxErrorCallAdapterFactory(RxJava2CallAdapterFactory.create(),
            _errorSubject
        )).build()
    return retrofit.create(T::class.java)
}

fun createOkHttpClient(_errorSubject:PublishSubject<Any>,headerInterceptor:Interceptor,certificatePinner:CertificatePinner?,getCache:Cache?): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    val client = OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .writeTimeout(60L, TimeUnit.SECONDS)
    if (BuildConfig.DEBUG) {
        client.addInterceptor(httpLoggingInterceptor)
    }
    client.interceptors().add(headerInterceptor)
    certificatePinner?.let {
        client.certificatePinner(it)
    }
    getCache?.let {
        client.cache(it)
    }
    client.authenticator { _, _ ->
        _errorSubject.onNext(NetworkResponseType.UnauthorizedEvent)
        return@authenticator null
    }
    return client.build()
}
inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    return retrofit.create(T::class.java)
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor).build()
}
private fun headerInterceptor(context: Context): Interceptor? {
    return Interceptor { chain ->
        val requestBuilder = chain.request().newBuilder()
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .header("Cache-control", "no-cache")
        requestBuilder.cacheControl(
            CacheControl.Builder()
                .maxAge(0, TimeUnit.SECONDS)
                .build()
        )
//            .addHeader("Authorization", "Bearer ")
        chain.proceed(requestBuilder.build())
    }

}
