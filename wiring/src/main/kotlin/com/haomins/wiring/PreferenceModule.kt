package com.haomins.wiring

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {

    private const val SHARED_PREFERENCE_NAME = "READER_SHARED_PREF"

    @Singleton
    @Provides
    fun provideSharedPreference(application: Application): SharedPreferences {
        return application.getSharedPreferences(
            SHARED_PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )
    }
}