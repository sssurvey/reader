package com.haomins.reader.view.fragments.settings

import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.haomins.reader.R
import dagger.hilt.android.AndroidEntryPoint

//TODO: complete feature
@AndroidEntryPoint
class ClearCacheFragment: PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_clear_cache, rootKey)
        configPreferences()
    }

    private fun configPreferences() {
        findPreference<Preference>(CACHE_SIZE)?.summary = "2.5 MB"
    }

    companion object {
        const val TAG = "ClearCacheFragment"
        private const val CACHE_SIZE = "cache_size"
    }
}