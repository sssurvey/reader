package com.haomins.domain.repositories.local

import com.haomins.model.entity.SubscriptionEntity
import io.reactivex.Completable
import io.reactivex.Single

interface SubscriptionLocalRepository {

    fun saveAllSubscriptionToLocal(sources: List<SubscriptionEntity>): Completable

    fun loadAllSubscriptionFromLocal(): Single<List<SubscriptionEntity>>

}