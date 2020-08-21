package com.haomins.reader

import android.app.Application
import com.haomins.reader.di.AppComponent
import com.haomins.reader.di.DaggerAppComponent
import com.haomins.reader.utils.DarkModeManager
import com.haomins.www.model.di.DaggerCoreComponent
import javax.inject.Inject

class ReaderApplication : Application() {

    @Inject
    lateinit var darkModeManager: DarkModeManager

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initAppComponent()
        initDarkMode()
    }

    private fun initAppComponent(): AppComponent {
        val coreComponent = DaggerCoreComponent.builder().application(this).build()
        appComponent = DaggerAppComponent.builder().setCoreComponent(coreComponent).build()
        appComponent.inject(this)
        return appComponent
    }

    private fun initDarkMode() {
        if (darkModeManager.checkIsCurrentDarkModeEnabled()) darkModeManager.enableDarkMode()
        else darkModeManager.disableDarkMode()
    }
}