package com.haomins.reader.utils.ui

import androidx.appcompat.app.AppCompatDelegate
import com.haomins.domain.common.SharedPrefUtils
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DarkModeManager @Inject constructor(
    private val sharedPrefUtils: SharedPrefUtils,
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
        return sharedPrefUtils.getBoolean(com.haomins.model.SharedPreferenceKey.IS_DARK_MODE_ENABLED)
    }

    private fun followSystemDarkMode(): Boolean {
        return !sharedPrefUtils.contains(com.haomins.model.SharedPreferenceKey.OVERRIDE_DARK_MODE_SETTINGS)
    }

    private fun saveDarkModeSettings(isEnabled: Boolean) {
        with(sharedPrefUtils) {
            putValue(com.haomins.model.SharedPreferenceKey.OVERRIDE_DARK_MODE_SETTINGS, true)
            putValue(com.haomins.model.SharedPreferenceKey.IS_DARK_MODE_ENABLED, isEnabled)
        }
    }
}