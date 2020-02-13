package com.haomins.reader.utils

import android.content.SharedPreferences

fun SharedPreferences.getValue(key: String): String {
    return this.getString(key, "")!!
}

fun SharedPreferences.putValue(key: String, value: String) {
    this.edit().putString(key, value).apply()
}

fun SharedPreferences.removeValue(key: String) {
    this.edit().remove(key).apply()
}