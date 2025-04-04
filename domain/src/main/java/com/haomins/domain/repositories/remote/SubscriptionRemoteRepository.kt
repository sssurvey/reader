package com.haomins.domain.repositories.remote

import com.haomins.model.remote.subscription.SubscriptionItemModel
import io.reactivex.Single

interface SubscriptionRemoteRepository {

    fun loadSubscriptionList(): Single<List<SubscriptionItemModel>>

}