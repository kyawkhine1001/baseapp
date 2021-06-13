package com.kkk.mylibrary.utils

import android.app.ProgressDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*


object SharedUtils {

    fun saveStringToShp(key: String, value: String, sharedPreferences: SharedPreferences) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    fun getStringFromShp(key: String, sharedPreferences: SharedPreferences): String? {
        return sharedPreferences.getString(key, null)
    }

    fun saveIntToShp(key: String, value: Int, sharedPreferences: SharedPreferences) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    fun getIntFromShp(key: String, sharedPreferences: SharedPreferences): Int {
        return sharedPreferences.getInt(key, SharedConstants.SHP_INT_ERR)
    }

    fun saveStringToShp(key: String, value: String, ctxt: Context) {
        val shp = ctxt.getSharedPreferences(SharedConstants.SHP_NAME, 0)
        shp!!.edit().putString(key, value).apply()
    }

    fun getStringFromShp(key: String, ctxt: Context): String? {
        val shp = ctxt.getSharedPreferences(SharedConstants.SHP_NAME, 0)
        return shp!!.getString(key, null)
    }

    fun saveIntToShp(key: String, value: Int, ctxt: Context) {
        val shp = ctxt.getSharedPreferences(SharedConstants.SHP_NAME, 0)
        shp!!.edit().putInt(key, value).apply()
    }

    fun getIntFromShp(key: String, ctxt: Context): Int? {
        val shp = ctxt.getSharedPreferences(SharedConstants.SHP_NAME, 0)
        return shp!!.getInt(key, SharedConstants.SHP_INT_ERR)
    }

    fun getDialog(ctxt: Context): ProgressDialog {
        val pd = ProgressDialog(ctxt)
        pd.setMessage(SharedConstants.PROGRESS_TEXT)
        return pd
    }


//    fun getJsonString(obj: Any): String {
//        val gson = Gson()
//        return gson.toJson(obj).toString()
//    }

//    @SuppressLint("InflateParams")
//    fun getAlertDialog(str: String, ctxt: Context): AlertDialog {
//        val ab: AlertDialog.Builder = AlertDialog.Builder(ctxt, R.style.InvitationDialog)
//        ab.setTitle("Alert")
//        val view = LayoutInflater.from(ctxt).inflate(R.layout.alert_dialog, null)
//        ab.setView(view)
//        view.alertMessage.text = str
//        ab.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
//        return ab.create()
//    }
//
//    @SuppressLint("InflateParams")
//    fun getProfileDialog(str: String, ctxt: Context): AlertDialog {
//        val ab: AlertDialog.Builder = AlertDialog.Builder(ctxt, R.style.InvitationDialog)
//        ab.setTitle("Profile")
//        val view = LayoutInflater.from(ctxt).inflate(R.layout.alert_dialog, null)
//        ab.setView(view)
//        view.alertMessage.text = str
//        ab.setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
//        return ab.create()
//    }
//
//    fun getListenerDailog(str: String, ctxt: Context, listener: DialogInterface.OnClickListener): AlertDialog.Builder {
//        var ab: AlertDialog.Builder = AlertDialog.Builder(ctxt, android.R.style.Animation_Dialog)
//        ab.setTitle("Alert")
//        ab.setMessage(str)
//        ab.setPositiveButton("ok", listener)
//        return ab
//    }

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
}