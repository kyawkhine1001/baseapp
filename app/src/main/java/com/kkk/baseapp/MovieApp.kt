package com.kkk.baseapp

import android.app.Application
import com.kkk.baseapp.di.appModule
import com.kkk.mylibrary.BaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level

class MovieApp : BaseApp() {
    override fun onCreate() {
        super.onCreate()
        org.koin.core.context.startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(applicationContext)
            modules(appModule)
        }
    }
}