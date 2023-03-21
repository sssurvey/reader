package com.haomins.reader.utils

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.haomins.data.util.contains
import com.haomins.data.util.getBoolean
import com.haomins.data.util.putValue
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DarkModeManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    fun initialize() {
        when {
            followSystemDarkMode() ->
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            checkIsCurrentDarkModeEnabled() ->
                enableDarkMode()
            else ->
                disableDarkMode()
        }
    }

    fun enableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        saveDarkModeSettings(true)
    }

    fun disableDarkMode() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        saveDarkModeSettings(false)
    }

    fun checkIsCurrentDarkModeEnabled(): Boolean {
        return sharedPreferences.getBoolean(com.haomins.data_model.SharedPreferenceKey.IS_DARK_MODE_ENABLED)
    }

    private fun followSystemDarkMode(): Boolean {
        return !sharedPreferences.contains(com.haomins.data_model.SharedPreferenceKey.OVERRIDE_DARK_MODE_SETTINGS)
    }

    private fun saveDarkModeSettings(isEnabled: Boolean) {
        with(sharedPreferences) {
            putValue(com.haomins.data_model.SharedPreferenceKey.OVERRIDE_DARK_MODE_SETTINGS, true)
            putValue(com.haomins.data_model.SharedPreferenceKey.IS_DARK_MODE_ENABLED, isEnabled)
        }
    }
}