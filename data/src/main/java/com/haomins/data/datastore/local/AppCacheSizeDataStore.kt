package com.haomins.data.datastore.local

import android.content.Context
import com.haomins.domain.repositories.local.AppCacheSizeRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Single
import java.io.File
import javax.inject.Inject

class AppCacheSizeDataStore @Inject constructor(
    @ApplicationContext private val context: Context
): AppCacheSizeRepository {

    override fun getCurrentCacheSize(): Single<Long> {
        return Single.fromCallable {
            var size = 0L
            context.filesDir.parentFile?.listFiles()?.forEach {
                if (it.isDirectory && it.name.contains(DATABASE_FOLDER_NAME)) {
                    size += getDirSize(it)
                }
            }
            size / (1024 * 1024)
        }
    }

    private fun getDirSize(file: File): Long {
        var size = 0L

        file.listFiles()?.forEach {
            size += if (it.isDirectory) getDirSize(it)
            else it.length()
        }

        return size
    }

    companion object {
        private const val DATABASE_FOLDER_NAME = "database"
    }

}