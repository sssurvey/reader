package com.haomins.www.model.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
object PreferenceModule {

    private const val SHARED_PREFERENCE_NAME = "READER_SHARED_PREF"

    @Reusable
    @JvmStatic
    @Provides
    fun provideSharedPreference(application: Application): SharedPreferences {
        return application.getSharedPreferences(
            SHARED_PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )
    }

}