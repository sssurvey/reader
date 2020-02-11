package com.haomins.reader.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.haomins.reader.ReaderApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PreferenceModule {

    @Provides
    @Singleton
    fun provideSharedPreference(application: Application): SharedPreferences {
        return application.getSharedPreferences(
            ReaderApplication.SHARED_PREFERENCE_NAME,
            Context.MODE_PRIVATE
        )
    }

}