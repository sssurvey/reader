package com.haomins.domain.usecase.article

import com.haomins.domain.exception.ParamsShouldNotBeNullException
import com.haomins.domain.repositories.ArticleListRepositoryContract
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.ObservableUseCase
import io.reactivex.Observable
import javax.inject.Inject

class ContinueLoadArticlesByFeed @Inject constructor(
    private val articleListRepositoryContract: ArticleListRepositoryContract,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : ObservableUseCase<ContinueLoadArticlesByFeed.Companion.Param, Unit>(
    executionScheduler = executionScheduler,
    postExecutionScheduler = postExecutionScheduler
) {

    override fun buildUseCaseObservable(params: Param?): Observable<Unit> {
        if (params == null) throw ParamsShouldNotBeNullException()
        return articleListRepositoryContract
            .continueLoadArticleItemRefs(params.feedId)
    }

    companion object {

        data class Param(
            val feedId: String
        )

        fun forContinueLoadArticlesByFeed(feedId: String): Param {
            return Param(feedId)
        }
    }
}