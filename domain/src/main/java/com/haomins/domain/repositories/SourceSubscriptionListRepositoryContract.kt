package com.haomins.domain.repositories

import com.haomins.domain_model.entities.SubscriptionEntity
import io.reactivex.Single

interface SourceSubscriptionListRepositoryContract {

    fun loadSubscriptionList(): Single<List<SubscriptionEntity>>

}