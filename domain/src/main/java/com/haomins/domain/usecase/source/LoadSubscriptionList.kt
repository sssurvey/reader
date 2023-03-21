package com.haomins.domain.usecase.source

import com.haomins.domain_model.entities.SubscriptionEntity
import com.haomins.domain.repositories.SourcesRepository
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class LoadSubscriptionList @Inject constructor(
    private val sourcesRepository: SourcesRepository,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : SingleUseCase<Unit, List<SubscriptionEntity>>(
    executionScheduler = executionScheduler,
    postExecutionScheduler = postExecutionScheduler
) {

    override fun buildUseCaseSingle(params: Unit?): Single<List<SubscriptionEntity>> {
        return sourcesRepository
            .loadSubscriptionList()
    }

}