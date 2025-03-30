package com.haomins.data.util

import android.content.SharedPreferences
import com.haomins.domain.common.SharedPrefUtils
import com.haomins.model.SharedPreferenceKey
import javax.inject.Inject

class SharedPrefUtilsImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : SharedPrefUtils {
    override fun getString(key: SharedPreferenceKey): String {
        return sharedPreferences.getString(key.string, "")!!
    }

    override fun getBoolean(key: SharedPreferenceKey): Boolean {
        return sharedPreferences.getBoolean(key.string, false)
    }

    override fun contains(key: SharedPreferenceKey): Boolean {
        return sharedPreferences.contains(key.string)
    }

    override fun putValue(key: SharedPreferenceKey, value: String) {
        sharedPreferences.edit().putString(key.string, value).apply()
    }

    override fun putValue(key: SharedPreferenceKey, value: Boolean) {
        sharedPreferences.edit().putBoolean(key.string, value).apply()
    }

    override fun removeValue(key: SharedPreferenceKey) {
        sharedPreferences.edit().remove(key.string).apply()
    }
}