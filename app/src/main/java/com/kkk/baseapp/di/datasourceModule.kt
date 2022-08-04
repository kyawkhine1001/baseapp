package com.kkk.baseapp.di

import com.kkk.baseapp.data.db.MyDatabase
import com.kkk.baseapp.network.ApiService
import com.kkk.baseapp.util.AppConstants
import com.kkk.mylibrary.network.createOkHttpClient
import com.kkk.mylibrary.network.createWebService
import org.koin.dsl.module

val remoteDatasourceModule = module  {
    single { createOkHttpClient() }
    single<ApiService> { createWebService(get(), AppConstants.baseUrl) }
}

val localDatasourceModule = module  {
    single { MyDatabase.getInstance(get()) }
}

val datasourceModule = listOf(remoteDatasourceModule, localDatasourceModule)