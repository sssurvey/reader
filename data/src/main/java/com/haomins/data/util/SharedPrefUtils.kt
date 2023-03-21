package com.haomins.data.util

import android.content.SharedPreferences
import com.haomins.model.SharedPreferenceKey

fun SharedPreferences.getString(key: SharedPreferenceKey): String {
    return this.getString(key.string, "")!!
}

fun SharedPreferences.getBoolean(key: SharedPreferenceKey): Boolean {
    return this.getBoolean(key.string, false)
}

fun SharedPreferences.contains(key: SharedPreferenceKey): Boolean {
    return this.contains(key.string)
}

fun SharedPreferences.putValue(key: SharedPreferenceKey, value: String) {
    this.edit().putString(key.string, value).apply()
}

fun SharedPreferences.putValue(key: SharedPreferenceKey, value: Boolean) {
    this.edit().putBoolean(key.string, value).apply()
}

fun SharedPreferences.removeValue(key: SharedPreferenceKey) {
    this.edit().remove(key.string).apply()
}