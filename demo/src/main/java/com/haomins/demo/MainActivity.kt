package com.haomins.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.haomins.ui.navigationbar.ReaderNavigationBar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        testNavBar()
    }

    private fun testNavBar() {
        with(findViewById<ReaderNavigationBar>(R.id.nav_bar)) {
            setOnClicks()
        }
    }
}