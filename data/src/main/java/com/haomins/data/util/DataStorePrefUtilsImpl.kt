package com.haomins.data.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.haomins.domain.common.PrefUtils
import com.haomins.domain.qualifiers.DefaultPrefDataStore
import com.haomins.model.PreferenceKey
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DataStorePrefUtilsImpl @Inject constructor(
    @DefaultPrefDataStore private val defaultPrefDataStore: DataStore<Preferences>,
) : PrefUtils {

    override suspend fun getString(key: PreferenceKey): String {
        return defaultPrefDataStore
            .data
            .first()[stringPreferencesKey(key.string)] ?: ""
    }

    override suspend fun getBoolean(key: PreferenceKey): Boolean {
        return defaultPrefDataStore
            .data
            .first()[booleanPreferencesKey(key.string)] == true
    }

    override suspend fun containsBoolean(key: PreferenceKey): Boolean {
        return defaultPrefDataStore
            .data
            .first()
            .contains(booleanPreferencesKey(name = key.string))
    }

    override suspend fun putValue(
        key: PreferenceKey,
        value: String
    ) {
        defaultPrefDataStore
            .edit {
                it[stringPreferencesKey(key.string)] = value
            }
    }

    override suspend fun putValue(
        key: PreferenceKey,
        value: Boolean
    ) {
        defaultPrefDataStore
            .edit {
                it[booleanPreferencesKey(key.string)] = value
            }
    }

    override suspend fun removeStringValue(key: PreferenceKey) {
        defaultPrefDataStore.edit {
            it.remove(stringPreferencesKey(key.string))
        }
    }

}