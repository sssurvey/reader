package com.haomins.domain.usecase.subscription

import com.haomins.domain.repositories.remote.SubscriptionRemoteRepository
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SingleUseCase
import com.haomins.model.remote.subscription.SubscriptionItemModel
import io.reactivex.Single
import javax.inject.Inject

class LoadSubscriptionListFromRemote @Inject constructor(
    private val subscriptionRemoteRepository: SubscriptionRemoteRepository,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : SingleUseCase<Unit, List<SubscriptionItemModel>>(
    executionScheduler = executionScheduler,
    postExecutionScheduler = postExecutionScheduler
) {

    override fun buildUseCaseSingle(params: Unit?): Single<List<SubscriptionItemModel>> {
        return subscriptionRemoteRepository
            .loadSubscriptionList()
    }

}