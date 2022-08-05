package com.kkk.baseapp.di.hilt

import android.content.Context
import com.kkk.baseapp.data.db.MyDatabase
import com.kkk.baseapp.data.repositories.EMarketRepository
import com.kkk.baseapp.data.repositories.EMarketRepositoryImpl
import com.kkk.baseapp.data.repositories.MainRepository
import com.kkk.baseapp.data.repositories.MainRepositoryImpl
import com.kkk.baseapp.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideMainRepo(@ApplicationContext context: Context,apiService: ApiService,database: MyDatabase):MainRepository = MainRepositoryImpl(context,apiService,database)

    @Provides
    fun provideEMarketRepo(@ApplicationContext context: Context,apiService: ApiService,database: MyDatabase):EMarketRepository = EMarketRepositoryImpl(context,apiService,database)

}