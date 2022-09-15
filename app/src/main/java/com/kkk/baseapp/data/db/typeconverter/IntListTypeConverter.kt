package com.kkk.baseapp.data.db.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class IntListTypeConverter {
    @TypeConverter
    fun fromString(value: String?): List<Int> {
        val listType: Type = object : TypeToken<List<Int?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromList(list: List<Int?>?): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}