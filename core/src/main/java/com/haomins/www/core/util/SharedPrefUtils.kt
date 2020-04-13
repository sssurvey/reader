package com.haomins.www.core.util

import android.content.SharedPreferences
import com.haomins.www.core.data.SharedPreferenceKey

fun SharedPreferences.getValue(key: SharedPreferenceKey): String {
    return this.getString(key.string, "")!!
}

fun SharedPreferences.putValue(key: SharedPreferenceKey, value: String) {
    this.edit().putString(key.string, value).apply()
}

fun SharedPreferences.removeValue(key: SharedPreferenceKey) {
    this.edit().remove(key.string).apply()
}