package com.haomins.domain.common

import com.haomins.model.PreferenceKey

interface PrefUtils {

    suspend fun getString(key: PreferenceKey): String

    suspend fun getBoolean(key: PreferenceKey): Boolean

    suspend fun containsBoolean(key: PreferenceKey): Boolean

    suspend fun putValue(key: PreferenceKey, value: String)

    suspend fun putValue(key: PreferenceKey, value: Boolean)

    suspend fun removeStringValue(key: PreferenceKey)
}