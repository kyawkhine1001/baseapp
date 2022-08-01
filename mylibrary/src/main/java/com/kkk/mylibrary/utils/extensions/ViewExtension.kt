package com.kkk.mylibrary.utils.extensions

import android.app.Activity
import android.content.Context
import android.os.Build
import android.text.TextUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import com.kkk.mylibrary.R
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

fun ImageView.loadImageWithGlide(context: Context,url:String){
        Glide
            .with(context)
            .load(url)
            .centerCrop()
            .thumbnail(Glide.with(getContext()).load(R.drawable.loading1))
            .into(this)
}

