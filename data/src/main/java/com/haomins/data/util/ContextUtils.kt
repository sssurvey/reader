package com.haomins.data.util

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContextUtils @Inject constructor(
    @ApplicationContext private val context: Context
) {

    internal fun getCacheDir(): File {
        return context.cacheDir
    }

}