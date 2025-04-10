package com.haomins.wiring

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.haomins.domain.qualifiers.DefaultPrefDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferenceModule {

    private const val DEFAULT_PREFERENCE_NAME = "READER_DEFAULT_PREF"

    @Singleton
    @Provides
    @DefaultPrefDataStore
    fun provideDefaultPrefDataStore(application: Application): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = {
                application.preferencesDataStoreFile(
                    "${application.packageName}" +
                            "." +
                            DEFAULT_PREFERENCE_NAME
                )
            }
        )
    }
}