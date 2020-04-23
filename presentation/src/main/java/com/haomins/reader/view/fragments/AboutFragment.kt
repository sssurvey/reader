package com.haomins.reader.view.fragments

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.haomins.reader.BuildConfig
import com.haomins.reader.R

class AboutFragment : PreferenceFragmentCompat() {

    companion object {
        const val TAG = "AboutFragment"
        private const val ABOUT_APP_VERSION = "app_version"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_about, rootKey)
        configPreferences()
    }

    private fun configPreferences() {
        findPreference<Preference>(ABOUT_APP_VERSION)?.summary = BuildConfig.VERSION_NAME
    }
}