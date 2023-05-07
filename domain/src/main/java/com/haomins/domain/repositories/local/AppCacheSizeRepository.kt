package com.haomins.domain.repositories.local

import io.reactivex.Completable
import io.reactivex.Single

interface AppCacheSizeRepository {

    fun getCurrentCacheSize(): Single<Long>

    fun clearCache(): Completable

}