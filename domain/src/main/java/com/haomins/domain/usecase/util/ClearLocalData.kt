package com.haomins.domain.usecase.util

import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.CompletableUseCase
import com.haomins.domain.usecase.HighLevelUseCase
import com.haomins.domain.usecase.article.ClearLocalArticles
import com.haomins.domain.usecase.subscription.local.ClearLocalSubscriptions
import io.reactivex.Completable
import javax.inject.Inject

@HighLevelUseCase(
    uses = [ClearLocalArticles::class, ClearLocalSubscriptions::class]
)
class ClearLocalData @Inject constructor(
    private val clearLocalArticles: ClearLocalArticles,
    private val clearLocalSubscriptions: ClearLocalSubscriptions,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : CompletableUseCase<Unit>(
    executionScheduler,
    postExecutionScheduler
) {

    override fun buildUseCaseCompletable(params: Unit?): Completable {
        return clearLocalArticles
            .buildUseCaseCompletable(Unit)
            .andThen(
                clearLocalSubscriptions.buildUseCaseCompletable(Unit)
            )
    }

}