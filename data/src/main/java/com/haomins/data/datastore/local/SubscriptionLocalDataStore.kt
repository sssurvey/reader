package com.haomins.data.datastore.local

import com.haomins.data.db.dao.SubscriptionDao
import com.haomins.domain.repositories.local.SubscriptionLocalRepository
import com.haomins.model.entity.SubscriptionEntity
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubscriptionLocalDataStore @Inject constructor(
    private val subscriptionDao: SubscriptionDao
) : SubscriptionLocalRepository {

    override fun saveAllSubscriptionToLocal(sources: List<SubscriptionEntity>): Completable {
        return subscriptionDao.insertAll(sources)
    }

    override fun loadAllSubscriptionFromLocal(): Single<List<SubscriptionEntity>> {
        return subscriptionDao.getAll()
    }

}