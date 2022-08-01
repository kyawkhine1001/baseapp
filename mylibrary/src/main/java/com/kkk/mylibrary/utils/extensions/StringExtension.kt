package com.kkk.mylibrary.utils.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.TextUtils
import com.google.gson.Gson
import com.kkk.mylibrary.R
import java.text.DecimalFormat

fun Double.mmCurrencyFormatWithTwoDigits(context: Context): String{
    val formattedString = this.formatDecimal()
    return context.getString(R.string.txt_mmk,formattedString)
}
fun Double.formatDecimal(): String {
//    val formatter = DecimalFormat("###,###,##0.00")
    val formatter = DecimalFormat("###,###,##0")//they don't want two digit decimal format
    return formatter.format(this)
}

fun Double.mmCurrencyFormatWithoutDigit(context: Context): String{
    val formattedString = String.format("%.0f", this)
    return context.getString(R.string.txt_mmk,formattedString)
}
fun String.isEmailValid(): Boolean {
    return !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
inline fun <reified T : Any> T.json(): String = Gson().toJson(this, T::class.java)
inline fun <reified T : Any> String?.fromJson(): T? = Gson().fromJson(this,T::class.java)

fun String.makeCall(context: Context) {
    this.let { phone ->
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
        context.startActivity(intent)
    }
}