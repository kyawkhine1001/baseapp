package com.kkk.baseapp.di.hilt

import android.content.Context
import com.kkk.baseapp.data.db.MyDatabase
import com.kkk.baseapp.domain.repo.EMarketRepository
import com.kkk.baseapp.data.repoImpl.EMarketRepositoryImpl
import com.kkk.baseapp.domain.repo.MainRepository
import com.kkk.baseapp.data.repoImpl.MainRepositoryImpl
import com.kkk.baseapp.network.ApiService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext


@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {

//    @Provides
//    fun provideMainRepo(
//        @ApplicationContext context: Context,
//        apiService: ApiService,
//        database: MyDatabase
//    ): MainRepository = MainRepositoryImpl(context, apiService, database)

    @Binds
    abstract fun bindMainRepository(repoImpl:MainRepositoryImpl):MainRepository


//    @Provides
//    fun provideEMarketRepo(
//        @ApplicationContext context: Context,
//        apiService: ApiService,
//        database: MyDatabase
//    ): EMarketRepository = EMarketRepositoryImpl(context, apiService, database)

    @Binds
    abstract fun bindEMarketRepository(repoImpl:EMarketRepositoryImpl):EMarketRepository
}