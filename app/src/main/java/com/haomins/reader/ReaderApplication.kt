package com.haomins.reader

import android.app.Activity
import android.app.Application
import androidx.fragment.app.Fragment
import com.haomins.reader.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class ReaderApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    companion object {
        const val SHARED_PREFERENCE_NAME = "READER_SHARED_PREF"
    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
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
}