package com.haomins.reader.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.haomins.reader.R
import com.haomins.reader.utils.showToast
import com.haomins.reader.view.activities.ArticleListActivity.Companion.LOAD_ALL_ITEM
import com.haomins.reader.view.activities.ArticleListActivity.Companion.SOURCE_FEED_ID
import com.haomins.reader.view.fragments.LoginFragment
import com.haomins.reader.view.fragments.SourceTitleListFragment
import com.haomins.reader.viewModels.MainActivityViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.app_bar.view.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        mainActivityViewModel =
            ViewModelProviders.of(this, viewModelFactory)[MainActivityViewModel::class.java]
        setContentView(R.layout.activity_main)
        handleLoginFragment()
        setOnClickListeners()
    }

    override fun onBackPressed() {
        when (mainActivityViewModel.hasAuthToken()) {
            true -> super.onBackPressed()
            false -> finish()
        }
    }

    fun startArticleListActivity(feedId: String) {
        val intent = Intent(this, ArticleListActivity::class.java)
        intent.putExtra(SOURCE_FEED_ID, feedId)
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
                else -> showToast("Still under development...")
            }
            closeDrawer()
            true
        }
    }

    private fun startArticleListActivityForAllItems() {
        val intent = Intent(this, ArticleListActivity::class.java)
        intent.putExtra(LOAD_ALL_ITEM, true)
        startActivity(intent)
    }

    private fun initDrawer() {
        initToolbar()
        navigation_view.itemIconTintList = null
        unlockDrawer()
    }

    private fun initToolbar() {
        appbar_layout.visibility = View.VISIBLE
        setSupportActionBar(toolbar)
        appbar_layout.toolbar.apply {
            setNavigationOnClickListener { openDrawer() }
            setNavigationIcon(R.drawable.ic_menu_24px)
        }
        supportActionBar?.apply {
            setDisplayShowTitleEnabled(false)
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
        when (mainActivityViewModel.hasAuthToken()) {
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
