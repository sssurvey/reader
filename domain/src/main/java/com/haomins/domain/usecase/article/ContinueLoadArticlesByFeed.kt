package com.haomins.domain.usecase.article

import com.haomins.domain.exception.ParamsShouldNotBeNullException
import com.haomins.domain.model.entities.ArticleEntity
import com.haomins.domain.repositories.ArticleListRepositoryContract
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class ContinueLoadArticlesByFeed @Inject constructor(
    private val articleListRepositoryContract: ArticleListRepositoryContract,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : SingleUseCase<ContinueLoadArticlesByFeed.Companion.Param, List<ArticleEntity>>(
    executionScheduler = executionScheduler,
    postExecutionScheduler = postExecutionScheduler
) {

    override fun buildUseCaseSingle(params: Param?): Single<List<ArticleEntity>> {
        if (params == null) throw ParamsShouldNotBeNullException()
        return articleListRepositoryContract
            .continueLoadArticleItems(params.feedId)
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