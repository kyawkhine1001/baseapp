package com.kkk.baseapp.di.hilt

import android.content.Context
import com.kkk.baseapp.data.db.MyDatabase
import com.kkk.baseapp.network.ApiService
import com.kkk.baseapp.util.AppConstants
import com.kkk.mylibrary.network.createOkHttpClient
import com.kkk.mylibrary.network.createWebService
import com.kkk.mylibrary.network.rx.AndroidSchedulerProvider
import com.kkk.mylibrary.network.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkDataSourceModule {

    @Singleton
    @Provides
    fun provideOkHttpClient():OkHttpClient{
        return createOkHttpClient()
    }

    @Singleton
    @Provides
    fun provideApiService(okHttpClient:OkHttpClient):ApiService{
        return createWebService<ApiService>(okHttpClient,AppConstants.baseUrl)
    }

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context):MyDatabase{
        return MyDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideScheduleProvider(): SchedulerProvider {
        return AndroidSchedulerProvider()
    }
}