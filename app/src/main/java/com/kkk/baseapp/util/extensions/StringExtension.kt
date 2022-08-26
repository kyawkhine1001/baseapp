package com.kkk.baseapp.util.extensions

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey


fun String.getStringPreferencesKey(): Preferences.Key<String>{
    return stringPreferencesKey(this)
}