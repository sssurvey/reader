package com.haomins.reader.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import com.haomins.reader.BuildConfig
import com.haomins.reader.R
import com.haomins.reader.ReaderApplication
import com.haomins.reader.utils.showToast
import com.haomins.reader.view.activities.ArticleListActivity.Companion.MODE
import com.haomins.reader.view.fragments.LoginFragment
import com.haomins.reader.view.fragments.SourceTitleListFragment
import com.haomins.reader.viewModels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.app_bar.view.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val mainViewModel by viewModels<MainViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as ReaderApplication).appComponent.viewModelComponent().build().inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handleLoginFragment()
        setOnClickListeners()
    }

    override fun onBackPressed() {
        when (mainViewModel.hasAuthToken()) {
            true -> super.onBackPressed()
            false -> finish()
        }
    }

    fun startArticleListActivity(feedId: String) {
        val intent = Intent(this, ArticleListActivity::class.java).apply {
            putExtra(MODE, ArticleListActivity.Mode.LOAD_BY_FEED_ID)
            putExtra(ArticleListActivity.Mode.LOAD_BY_FEED_ID.key, feedId)
        }
        startActivity(intent)
    }

    fun showSourceTitleListFragment() {
        initDrawer()
        supportFragmentManager.beginTransaction().replace(
            R.id.main_activity_frame_layout,
            SourceTitleListFragment(),
            SourceTitleListFragment.TAG
        ).commit()
    }

    private fun setOnClickListeners() {
        setNavigationViewOnItemClickListener()
    }

    private fun setNavigationViewOnItemClickListener() {
        navigation_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.option_all_articles -> startArticleListActivityForAllItems()
                R.id.option_settings -> startSettingsActivity()
                R.id.option_add_source -> startAddSourceActivity()
                else -> showToast("Still under development...")
            }
            closeDrawer()
            true
        }
    }

    private fun startArticleListActivityForAllItems() {
        val intent = Intent(this, ArticleListActivity::class.java).apply {
            putExtra(MODE, ArticleListActivity.Mode.LOAD_ALL)
        }
        startActivity(intent)
    }

    private fun startSettingsActivity() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }

    private fun startAddSourceActivity() {
        startActivity(Intent(this, AddSourceActivity::class.java))
    }

    private fun initDrawer() {
        initToolbar()
        navigation_view.itemIconTintList = null
        navigation_view.drawer_login_app_version_text_view.text =
            getString(R.string.version_description, BuildConfig.VERSION_NAME)
        unlockDrawer()
    }

    private fun initToolbar() {
        appbar_layout.visibility = View.VISIBLE
        setSupportActionBar(toolbar)
        appbar_layout.toolbar.apply {
            setNavigationOnClickListener { openDrawer() }
            setNavigationIcon(R.drawable.ic_menu)
        }
    }

    private fun lockDrawer() {
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    private fun unlockDrawer() {
        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    private fun openDrawer() {
        drawer_layout.openDrawer(GravityCompat.START)
    }

    private fun closeDrawer() {
        drawer_layout.closeDrawer(GravityCompat.START)
    }

    private fun handleLoginFragment() {
        lockDrawer()
        when (mainViewModel.hasAuthToken()) {
            true -> showSourceTitleListFragment()
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
