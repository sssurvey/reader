package com.haomins.domain.usecase.article

import com.haomins.domain.exception.ParamsShouldNotBeNullException
import com.haomins.domain.model.entities.ArticleEntity
import com.haomins.domain.repositories.ArticleListRepositoryContract
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.ObservableUseCase
import io.reactivex.Observable
import javax.inject.Inject

class LoadArticlesByFeed @Inject constructor(
    private val articleListRepositoryContract: ArticleListRepositoryContract,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : ObservableUseCase<LoadArticlesByFeed.Companion.Param, List<ArticleEntity>>(
    executionScheduler = executionScheduler,
    postExecutionScheduler = postExecutionScheduler
) {

    override fun buildUseCaseObservable(params: Param?): Observable<List<ArticleEntity>> {
        if (params == null) throw ParamsShouldNotBeNullException()
        return articleListRepositoryContract
            .loadArticleItemRefs(params.feedId)
    }

    companion object {

        data class Param(
            val feedId: String
        )

        fun forLoadArticlesByFeed(feedId: String): Param {
            return Param(feedId)
        }
    }
}