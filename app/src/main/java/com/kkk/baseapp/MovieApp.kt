package com.kkk.baseapp

import com.kkk.baseapp.di.koin.appModule
import com.kkk.mylibrary.BaseApp
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level

@HiltAndroidApp
class MovieApp : BaseApp() {
    override fun onCreate() {
        super.onCreate()
//        org.koin.core.context.startKoin {
//            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
//            androidContext(applicationContext)
//            modules(appModule)
//        }
    }
}