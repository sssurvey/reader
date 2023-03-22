package com.haomins.domain.repositories.local

import com.haomins.model.entity.SubscriptionEntity
import io.reactivex.Completable
import io.reactivex.Single

interface SourceLocalRepository {

    fun saveAllSourcesToLocal(sources: List<SubscriptionEntity>): Completable

    fun loadAllSourcesFromLocal(): Single<List<SubscriptionEntity>>

}