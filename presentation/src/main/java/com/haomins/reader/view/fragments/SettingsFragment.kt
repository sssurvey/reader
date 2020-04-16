package com.haomins.reader.view.fragments

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.haomins.reader.R

class SettingsFragment : PreferenceFragmentCompat() {

    companion object {
        const val TAG = "SettingsFragment"
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey)
    }

}