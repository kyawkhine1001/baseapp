package com.kkk.mylibrary.utils

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*


object SharedUtils {

    fun saveStringToShp(key: String, value: String, sharedPreferences: SharedPreferences) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getStringFromShp(key: String, sharedPreferences: SharedPreferences): String? {
        return sharedPreferences.getString(key, null)
    }

    fun getDialog(ctxt: Context): ProgressDialog {
        val pd = ProgressDialog(ctxt)
        pd.setMessage(SharedConstants.PROGRESS_TEXT)
        return pd
    }


    fun getJsonString(obj: Any): String {
        val gson = Gson()
        return gson.toJson(obj).toString()
    }

    fun getProfileDialog(title: String?=null, buttonText: String, @LayoutRes layout:Int, ctxt: Context): AlertDialog.Builder {
        val ab: AlertDialog.Builder = AlertDialog.Builder(ctxt)
        title?.let {
            ab.setTitle(it)
        }
        val view = LayoutInflater.from(ctxt).inflate(layout, null)
        ab.setView(view)
        ab.setPositiveButton(buttonText) { dialog, _ -> dialog.dismiss() }
        return ab
    }

    fun getAlertDailog(title:String,message: String,buttonText:String, ctxt: Context, listener: DialogInterface.OnClickListener): AlertDialog.Builder {
        var ab: AlertDialog.Builder = AlertDialog.Builder(ctxt, android.R.style.Animation_Dialog)
        ab.setTitle(title)
        ab.setMessage(message)
        ab.setPositiveButton(buttonText, listener)
        return ab
    }

    fun getAppVersion(context: Context): String {
        try {
            val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            val version = pInfo.versionName
            return "Version - $version"
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        return ""
    }

    fun getCurrentDate(withTime: Boolean): String {
        var dateFormat = "yyyy-MM-dd"
        if (withTime) {
            dateFormat += " HH:mm:ss"
        }
        return SimpleDateFormat(dateFormat).format(Date())
    }

    fun getStringFromShp(salemanId: String, s: String): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun showToast(message:String,context:Context){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

    fun isNetworkAvailable(context: Context): Boolean {
        val service = Context.CONNECTIVITY_SERVICE
        val manager = context.getSystemService(service) as ConnectivityManager?
        val network = manager?.activeNetworkInfo
        return (network != null)
    }
}