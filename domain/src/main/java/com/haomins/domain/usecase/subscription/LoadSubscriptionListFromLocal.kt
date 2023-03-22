package com.haomins.domain.usecase.subscription

import com.haomins.domain.repositories.local.SubscriptionLocalRepository
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SingleUseCase
import com.haomins.model.entity.SubscriptionEntity
import io.reactivex.Single
import javax.inject.Inject

class LoadSubscriptionListFromLocal @Inject constructor(
    private val subscriptionLocalRepository: SubscriptionLocalRepository,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : SingleUseCase<Unit, List<SubscriptionEntity>>(
    executionScheduler,
    postExecutionScheduler
) {

    override fun buildUseCaseSingle(params: Unit?): Single<List<SubscriptionEntity>> {
        return subscriptionLocalRepository.loadAllSubscriptionFromLocal()
    }

}