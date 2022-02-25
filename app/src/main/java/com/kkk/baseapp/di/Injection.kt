package com.kkk.androidarchitectures.di

import android.content.Context
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.kkk.baseapp.data.db.MyDatabase
import com.kkk.baseapp.data.repositories.MainRepository
import com.kkk.baseapp.data.repositories.MainRepositoryImpl
import com.kkk.baseapp.network.ApiService
import com.kkk.baseapp.util.AppConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Injection {

    private fun provideApiService(): ApiService {
        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(AppConstants.baseUrl)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(ApiService::class.java)
    }

    private fun provideDatabase(context:Context): MyDatabase {
        return MyDatabase.getInstance(context)
    }

    fun provideMainRepository(context: Context): MainRepository {
        return MainRepositoryImpl(context,provideApiService(), provideDatabase(context))
    }

}