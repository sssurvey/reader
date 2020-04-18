package com.haomins.reader

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.haomins.reader.di.DaggerAppComponent
import com.haomins.reader.utils.DarkModeManager
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class ReaderApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var darkModeManager: DarkModeManager

    override fun onCreate() {
        super.onCreate()
        initDagger()
        initDarkMode()
    }

    override fun activityInjector() = activityDispatchingAndroidInjector

    override fun supportFragmentInjector() = fragmentDispatchingAndroidInjector

    private fun initDagger() {
        DaggerAppComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }

    private fun initDarkMode() {
        if(darkModeManager.checkIsCurrentDarkModeEnabled()) darkModeManager.enableDarkMode()
        else darkModeManager.disableDarkMode()
    }
}