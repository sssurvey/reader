package com.haomins.reader.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.haomins.reader.R
import com.haomins.reader.databinding.ActivityMainBinding
import com.haomins.reader.view.activities.ArticleListActivity.Companion.LOAD_MODE_KEY
import com.haomins.reader.view.fragments.LoginFragment
import com.haomins.reader.view.fragments.SubscriptionListFragment
import com.haomins.reader.view.fragments.settings.DisclosureFragment
import com.haomins.reader.viewModels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inflateView()
        handleLoginFragment()
    }

    override fun onBackPressed() {
        when (mainViewModel.hasAuthToken()) {
            true -> super.onBackPressed()
            false -> this.handleOnBackPressed()
        }
    }

    fun startArticleListActivity(feedId: String) {
        Intent(this, ArticleListActivity::class.java).apply {
            putExtra(LOAD_MODE_KEY, ArticleListActivity.LoadMode.LOAD_BY_FEED)
            putExtra(ArticleListActivity.LoadMode.LOAD_BY_FEED.key, feedId)
        }.let {
            startActivity(it)
        }
    }

    fun startSettingsActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    fun startArticleListActivityForAllItems() {
        val intent = Intent(this, ArticleListActivity::class.java).apply {
            putExtra(LOAD_MODE_KEY, ArticleListActivity.LoadMode.LOAD_ALL)
        }
        startActivity(intent)
    }

    fun startAddSourceActivity() {
        startActivity(Intent(this, AddSourceActivity::class.java))
    }

    fun showDisclosureFragment() {
        supportFragmentManager.beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .replace(
                R.id.main_activity_frame_layout,
                DisclosureFragment(),
                DisclosureFragment.TAG
            )
            .addToBackStack(null)
            .commit()
    }

    fun showAfterUserLoggedInFragment() {

        fun showSubscriptionListFragment() {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.main_activity_frame_layout,
                    SubscriptionListFragment(),
                    SubscriptionListFragment.TAG
                )
                .commit()
        }

        showSubscriptionListFragment()
    }

    private fun inflateView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    private fun handleOnBackPressed() {
        if (supportFragmentManager.backStackEntryCount >= 1) supportFragmentManager.popBackStack()
        else finish()
    }

    private fun handleLoginFragment() {
        when (mainViewModel.hasAuthToken()) {
            true -> showAfterUserLoggedInFragment()
            false -> showUserLoginFragment()
        }
    }

    private fun showUserLoginFragment() {
        supportFragmentManager.beginTransaction().replace(
            R.id.main_activity_frame_layout,
            LoginFragment(),
            LoginFragment.TAG
        ).commit()
    }

}
