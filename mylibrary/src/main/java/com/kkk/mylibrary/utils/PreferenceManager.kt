package com.kkk.mylibrary.utils

import android.content.Context
import android.content.SharedPreferences

class PreferenceManager(context: Context,name:String)  {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(name, Context.MODE_PRIVATE)

    fun setStringData(key: String, value: String) {
        preferences.edit().putString(key, value).apply()
    }

    fun getStringData(key: String): String? {
        return preferences.getString(key, "")
    }

    fun setIntData(key: String, value: Int) {
        preferences.edit().putInt(key, value).apply()
    }

    fun getIntData(key: String): Int {
        return preferences.getInt(key, 0)
    }

    fun setLongData(key: String, value: Long) {
        preferences.edit().putLong(key, value).apply()
    }

    fun getLongData(key: String): Long {
        return preferences.getLong(key, 0)
    }

    fun setBooleanData(key: String, value: Boolean) {
        preferences.edit().putBoolean(key, value).apply()
    }

    fun getBooleanData(key: String): Boolean {
        return preferences.getBoolean(key, false)
    }


}
