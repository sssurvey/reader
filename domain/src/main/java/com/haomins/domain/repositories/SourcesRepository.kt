package com.haomins.domain.repositories

import com.haomins.model.entity.SubscriptionEntity
import io.reactivex.Single

interface SourcesRepository {

    fun loadSubscriptionList(): Single<List<SubscriptionEntity>>

}