package com.haomins.reader

import android.app.Application
import com.haomins.reader.di.AppComponent
import com.haomins.reader.di.DaggerAppComponent
import com.haomins.reader.utils.DarkModeManager
import com.haomins.www.core.db.RealmUtil
import com.haomins.www.core.di.DaggerCoreComponent
import javax.inject.Inject

class ReaderApplication : Application() {

    @Inject
    lateinit var darkModeManager: DarkModeManager

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
        initRealm()
        initDarkMode()
    }

    private fun initAppComponent() {
        val coreComponent = DaggerCoreComponent.builder().application(this).build()
        appComponent = DaggerAppComponent.builder().setCoreComponent(coreComponent).build()
        appComponent.inject(this)
    }

    private fun initRealm() {
        RealmUtil.init(this)
    }

    private fun initDarkMode() {
        if (darkModeManager.checkIsCurrentDarkModeEnabled()) darkModeManager.enableDarkMode()
        else darkModeManager.disableDarkMode()
    }
}