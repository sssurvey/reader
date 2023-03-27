package com.haomins.reader

//import com.haomins.www.model.di.DaggerDataComponent
import android.app.Application
import com.haomins.reader.utils.ui.DarkModeManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class ReaderApplication : Application() {

    @Inject
    lateinit var darkModeManager: DarkModeManager

    override fun onCreate() {
        super.onCreate()
        initDarkMode()
    }

    private fun initDarkMode() {
        darkModeManager.initialize()
    }
}