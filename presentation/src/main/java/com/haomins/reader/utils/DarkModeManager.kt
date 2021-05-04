package com.haomins.reader.utils

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.haomins.www.data.model.SharedPreferenceKey
import com.haomins.www.data.util.getBoolean
import com.haomins.www.data.util.putValue
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DarkModeManager @Inject constructor(
        private val sharedPreferences: SharedPreferences
) {

    fun enableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        saveDarkModeSettings(true)
    }

    fun disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        saveDarkModeSettings(false)
    }

    fun checkIsCurrentDarkModeEnabled(): Boolean {
        return sharedPreferences.getBoolean(SharedPreferenceKey.IS_DARK_MODE_ENABLED)
    }

    private fun saveDarkModeSettings(isEnabled: Boolean) {
        sharedPreferences.putValue(SharedPreferenceKey.IS_DARK_MODE_ENABLED, isEnabled)
    }
}