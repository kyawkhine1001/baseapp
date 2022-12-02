package com.kkk.baseapp

import com.kkk.mylibrary.BaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : BaseApp() {
    override fun onCreate() {
        super.onCreate()
//        org.koin.core.context.startKoin {
//            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
//            androidContext(applicationContext)
//            modules(appModule)
//        }
    }
}