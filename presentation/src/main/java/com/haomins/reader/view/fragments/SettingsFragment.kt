package com.haomins.reader.view.fragments

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.haomins.reader.R

class SettingsFragment : PreferenceFragmentCompat() {

    companion object {
        const val TAG = "SettingsFragment"
        const val DARK_MODE_ENABLED_FLAG = 32
        const val DARK_MODE_DISABLED_FLAG = 16
        private const val DARK_MODE_OPTION = "dark_mode"
    }

    private val darkModeSwitchPreferenceCompat by lazy {
        findPreference<SwitchPreferenceCompat>(DARK_MODE_OPTION)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey)
        configPreference()
        setOnclickListeners()
    }

    private fun configPreference() {
        when ((resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK)) {
            DARK_MODE_ENABLED_FLAG -> darkModeSwitchPreferenceCompat?.isChecked = true
            DARK_MODE_DISABLED_FLAG -> darkModeSwitchPreferenceCompat?.isChecked = false
        }
    }

    private fun setOnclickListeners() {
        darkModeSwitchPreferenceCompat?.apply {
            isPersistent = true
            setOnPreferenceClickListener {
                when (isChecked) {
                    true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
                true
            }
        }
    }
}