package com.haomins.domain.usecase.subscription

import com.haomins.domain.repositories.SubscriptionRemoteRepository
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SingleUseCase
import com.haomins.model.entity.SubscriptionEntity
import io.reactivex.Single
import javax.inject.Inject

class LoadSubscriptionListFromRemote @Inject constructor(
    private val subscriptionRemoteRepository: SubscriptionRemoteRepository,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : SingleUseCase<Unit, List<SubscriptionEntity>>(
    executionScheduler = executionScheduler,
    postExecutionScheduler = postExecutionScheduler
) {

    override fun buildUseCaseSingle(params: Unit?): Single<List<SubscriptionEntity>> {
        return subscriptionRemoteRepository
            .loadSubscriptionList()
    }

}