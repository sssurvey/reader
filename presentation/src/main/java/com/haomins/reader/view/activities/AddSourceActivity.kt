package com.haomins.reader.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.haomins.reader.R
import com.haomins.reader.databinding.ActivityAddSourceBinding
import com.haomins.reader.utils.slideInAnimation
import com.haomins.reader.utils.slideOutAnimation
import com.haomins.reader.view.fragments.AddSourceFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddSourceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddSourceBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflateView()
        slideInAnimation()
        showAddSourceFragment()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        slideOutAnimation()
    }

    private fun inflateView() {
        binding = ActivityAddSourceBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun showAddSourceFragment() {
        supportFragmentManager.beginTransaction().replace(
            R.id.add_source_activity_frame_layout,
            AddSourceFragment(),
            AddSourceFragment.TAG
        ).commit()
    }
}