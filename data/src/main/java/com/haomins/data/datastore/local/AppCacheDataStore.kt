package com.haomins.data.datastore.local

import android.content.Context
import android.util.Log
import com.haomins.domain.repositories.local.AppCacheSizeRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File
import javax.inject.Inject

class AppCacheDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) : AppCacheSizeRepository {

    override fun getCurrentCacheSize(): Single<Long> {
        return Single.fromCallable {
            var size = 0L
            context.cacheDir.listFiles()?.forEach {
                size += if (it.isDirectory) getDirSize(it)
                else it.length()
            }
            size / (1024 * 1024)
        }.doOnSuccess {
            Log.d(TAG, "current cache size is -> $it MB")
        }
    }

    override fun clearCache(): Completable {
        return Completable.fromCallable {
            context.cacheDir.listFiles()?.forEach {
                if (it.isDirectory) deleteDir(it)
                else deleteFile(it)
            }
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

    private fun deleteDir(file: File) {
        file.listFiles()?.forEach {
            if (it.isDirectory) deleteDir(it)
            else deleteFile(it)
        }
    }

    private fun deleteFile(file: File) {
        Log.d(TAG, "deleted: ${file.name}")
        file.delete()
    }

    companion object {
        private const val TAG = "AppCacheSizeDataStore"
    }
}