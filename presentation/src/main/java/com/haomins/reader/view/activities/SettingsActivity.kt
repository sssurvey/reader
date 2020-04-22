package com.haomins.reader.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.haomins.reader.R
import com.haomins.reader.view.fragments.AboutFragment
import com.haomins.reader.view.fragments.SettingsFragment

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        showSettingsFragment()
    }

    fun showAboutFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.settings_container,
                AboutFragment(),
                AboutFragment.TAG
            )
            .addToBackStack(null)
            .commit()
    }

    private fun showSettingsFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.settings_container,
                SettingsFragment(),
                SettingsFragment.TAG
            )
            .commit()
    }

}