package com.kkk.mylibrary

import android.app.Application
import androidx.lifecycle.MutableLiveData
import java.util.*

open class BaseApp:Application() {
    val appLanguage = MutableLiveData<Locale>()
}