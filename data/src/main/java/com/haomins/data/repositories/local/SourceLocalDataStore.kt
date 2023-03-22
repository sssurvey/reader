package com.haomins.data.repositories.local

import com.haomins.data.db.dao.SubscriptionDao
import com.haomins.domain.repositories.local.SourceLocalRepository
import com.haomins.model.entity.SubscriptionEntity
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SourceLocalDataStore @Inject constructor(
    private val subscriptionDao: SubscriptionDao
) : SourceLocalRepository {

    override fun saveAllSourcesToLocal(sources: List<SubscriptionEntity>): Completable {
        return subscriptionDao.insertAllV2(sources)
    }

    override fun loadAllSourcesFromLocal(): Single<List<SubscriptionEntity>> {
        return subscriptionDao.getAllV2()
    }

}