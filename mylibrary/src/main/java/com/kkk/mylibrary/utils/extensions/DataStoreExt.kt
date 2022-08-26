package com.kkk.mylibrary.utils.extensions

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

//Datastore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "kkk_base")

suspend fun Context.setStringData(key: Preferences.Key<String>, value: String) {
    dataStore.edit { it[key] = value }
}

fun Context.getStringData(key: Preferences.Key<String>) = dataStore.data
    .map { preferences -> preferences[key] }

suspend fun Context.deleteStringData(key: Preferences.Key<String>) {
    dataStore.edit {
        it.remove(key)
    }
}
fun String.getStringPreferencesKey():Preferences.Key<String>{
    return stringPreferencesKey(this)
}