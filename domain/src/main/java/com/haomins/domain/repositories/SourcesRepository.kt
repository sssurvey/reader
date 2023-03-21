package com.haomins.domain.repositories

import com.haomins.domain_model.entities.SubscriptionEntity
import io.reactivex.Single

interface SourcesRepository {

    fun loadSubscriptionList(): Single<List<SubscriptionEntity>>

}