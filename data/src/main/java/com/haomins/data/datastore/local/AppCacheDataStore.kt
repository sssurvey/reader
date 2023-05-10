package com.haomins.data.datastore.local

import android.util.Log
import com.haomins.data.util.ContextUtils
import com.haomins.domain.repositories.local.AppCacheSizeRepository
import io.reactivex.Completable
import io.reactivex.Single
import java.io.File
import javax.inject.Inject

class AppCacheDataStore @Inject constructor(
    private val contextUtils: ContextUtils
) : AppCacheSizeRepository {

    override fun getCurrentCacheSize(): Single<Long> {
        return Single.fromCallable {
            var size = 0L
            contextUtils.getCacheDir().listFiles()?.forEach {
                size += if (it.isDirectory) getDirSize(it)
                else it.length()
            }
            size / MB_OFFSET
        }.doOnSuccess {
            Log.d(TAG, "current cache size is -> $it MB")
        }
    }

    override fun clearCache(): Completable {
        return Completable.fromCallable {
            contextUtils.getCacheDir().listFiles()?.forEach {
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
        private const val MB_OFFSET = 1024 * 1024
    }
}