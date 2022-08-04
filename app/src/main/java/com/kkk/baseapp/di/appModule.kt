package com.kkk.baseapp.di

import com.kkk.baseapp.data.repositories.EMarketRepository
import com.kkk.baseapp.data.repositories.EMarketRepositoryImpl
import com.kkk.baseapp.data.repositories.MainRepository
import com.kkk.baseapp.data.repositories.MainRepositoryImpl
import com.kkk.baseapp.viewmodel.EMarketViewModel
import com.kkk.baseapp.viewmodel.MainViewModel
import com.kkk.mylibrary.network.rx.AndroidSchedulerProvider
import com.kkk.mylibrary.network.rx.SchedulerProvider
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val movieModule = module {

    //provide data repository
    single<MainRepository> { MainRepositoryImpl(get(),get(),get()) }


    single<EMarketRepository> { EMarketRepositoryImpl(get(),get(),get()) }

//    ViewModel for Home
    viewModel { MainViewModel(get(), get()) }

    viewModel { EMarketViewModel(get(), get()) }

}

val rxModule = module {
    //provide schedule provider
    factory<SchedulerProvider> { AndroidSchedulerProvider() }
}

val appModule = listOf(rxModule, movieModule)+datasourceModule