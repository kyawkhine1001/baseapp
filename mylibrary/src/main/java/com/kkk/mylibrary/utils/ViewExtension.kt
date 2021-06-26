package com.kkk.mylibrary.utils

import android.app.Activity
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import java.util.*

@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
fun Activity.setCurrentLocale() {
//        val locale = Locale(Preferences.getCurrentLocale(this))
    val locale = Locale("en")
    val config = resources.configuration
    Locale.setDefault(locale)
    config.setLocale(locale)
    resources.updateConfiguration(config, resources.displayMetrics)
}

fun Context.showToast(@StringRes resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_LONG).show()
}

fun Context.showToast(msg: String?) {
    Toast.makeText(this, "$msg", Toast.LENGTH_LONG).show()
}