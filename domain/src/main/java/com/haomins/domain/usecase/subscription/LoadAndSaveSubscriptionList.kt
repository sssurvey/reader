package com.haomins.domain.usecase.subscription

import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SingleUseCase
import com.haomins.model.entity.SubscriptionEntity
import io.reactivex.Single
import javax.inject.Inject

class LoadAndSaveSubscriptionList @Inject constructor(
    private val loadSubscriptionListFromRemote: LoadSubscriptionListFromRemote,
    private val saveSubscriptionListToLocal: SaveSubscriptionListToLocal,
    private val loadSubscriptionListFromLocal: LoadSubscriptionListFromLocal,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : SingleUseCase<Unit, List<SubscriptionEntity>>(
    executionScheduler,
    postExecutionScheduler
) {

    override fun buildUseCaseSingle(params: Unit?): Single<List<SubscriptionEntity>> {
        return loadSubscriptionListFromRemote
            .buildUseCaseSingle(Unit)
            .flatMapCompletable {
                saveSubscriptionListToLocal
                    .buildUseCaseCompletable(
                        SaveSubscriptionListToLocal.forSaveSubscriptionList(it)
                    )
            }
            .andThen(
                loadSubscriptionListFromLocal.buildUseCaseSingle(Unit)
            )
            .onErrorResumeNext {
                loadSubscriptionListFromLocal.buildUseCaseSingle(Unit)
            }
    }
}