package com.kkk.mylibrary.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.material.snackbar.Snackbar
import com.kkk.mylibrary.R
import java.util.*

fun Activity.isGooglePlayServicesAvailable(): Boolean {
    val googleApiAvailability = GoogleApiAvailability.getInstance()
    val status = googleApiAvailability.isGooglePlayServicesAvailable(this)
    return status == ConnectionResult.SUCCESS
}

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

fun View.showSnackbar(showText:CharSequence, actionText:String?, actionListener:()->Unit?) =
    run {
        val snackbar = Snackbar.make(this, showText, Snackbar.LENGTH_LONG)
        actionText?.let {
            snackbar.setAction(it) { actionListener() }
        }
        snackbar.show()
    }

fun ImageView.loadImageWithGlide(context: Context,url:String){
        Glide
            .with(context)
            .load(url)
            .centerCrop()
            .thumbnail(Glide.with(getContext()).load(R.drawable.loading1))
            .into(this)
}

fun Context.showImage(
    imageView: ImageView,
    imageUrl: String?,
    errorHandlerView: Drawable? = ContextCompat.getDrawable(this, R.drawable.loading1),
    placeHolder: Drawable? = ContextCompat.getDrawable(this, R.drawable.loading1)
) =
    Glide.with(this)
        .load(imageUrl)
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .placeholder(placeHolder)
        .priority(Priority.LOW)
        .error(errorHandlerView)
        .into(imageView)


inline fun <reified T : AppCompatActivity> AppCompatActivity.goIntentClearFlag(vararg params: Pair<String, String>) {
    val intent = Intent(this, T::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    params.forEach { intent.putExtra(it.first, it.second) }
    startActivity(intent)
}