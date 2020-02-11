package com.haomins.reader.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.haomins.reader.R
import com.haomins.reader.fragments.login.LoginFragment

class MainActivity : AppCompatActivity() {

    private val mainActivityViewModel by lazy {
        ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (mainActivityViewModel.isUserLoggedIn()) {
            // no op
        } else {
            showUserLoginFragment()
        }
    }

    private fun showUserLoginFragment() {
        supportFragmentManager.beginTransaction().replace(
            R.id.main_activity_frame_layout,
            LoginFragment()
        ).addToBackStack(LoginFragment.TAG).commit()
    }
}
