package com.haomins.domain.usecase.subscription.local

import com.haomins.domain.repositories.local.SubscriptionLocalRepository
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.CompletableUseCase
import io.reactivex.Completable
import javax.inject.Inject

class ClearLocalSubscriptions @Inject constructor(
    private val subscriptionLocalRepository: SubscriptionLocalRepository,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
): CompletableUseCase<Unit>(
    executionScheduler,
    postExecutionScheduler
) {

    override fun buildUseCaseCompletable(params: Unit?): Completable {
        return subscriptionLocalRepository.clearAllSubscriptions()
    }

}