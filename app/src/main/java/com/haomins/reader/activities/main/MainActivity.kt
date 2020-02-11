package com.haomins.reader.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.haomins.reader.R
import com.haomins.reader.fragments.login.LoginFragment

class MainActivity : AppCompatActivity() {

    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
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

    private fun init() {
        mainActivityViewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
    }
}
