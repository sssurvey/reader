package com.haomins.domain.common

import com.haomins.model.SharedPreferenceKey

interface SharedPrefUtils {

    fun getString(key: SharedPreferenceKey): String

    fun getBoolean(key: SharedPreferenceKey): Boolean

    fun contains(key: SharedPreferenceKey): Boolean

    fun putValue(key: SharedPreferenceKey, value: String)

    fun putValue(key: SharedPreferenceKey, value: Boolean)

    fun removeValue(key: SharedPreferenceKey)
}