package com.haomins.domain.usecase.source

import com.haomins.domain.exception.ParamsShouldNotBeNullException
import com.haomins.domain.repositories.local.SourceLocalRepository
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.CompletableUseCase
import com.haomins.model.entity.SubscriptionEntity
import io.reactivex.Completable
import javax.inject.Inject

class SaveSubscriptionListToLocal @Inject constructor(
    private val sourcesLocalRepository: SourceLocalRepository,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : CompletableUseCase<SaveSubscriptionListToLocal.Companion.Params>(
    executionScheduler,
    postExecutionScheduler
) {

    override fun buildUseCaseCompletable(params: Params?): Completable {
        if (params == null) throw ParamsShouldNotBeNullException()
        return sourcesLocalRepository.saveAllSourcesToLocal(params.subscriptionEntities)
    }

    companion object {

        data class Params(
            val subscriptionEntities: List<SubscriptionEntity>
        )

        fun forSaveSubscriptionList(subscriptionEntities: List<SubscriptionEntity>): Params {
            return Params(
                subscriptionEntities
            )
        }
    }
}