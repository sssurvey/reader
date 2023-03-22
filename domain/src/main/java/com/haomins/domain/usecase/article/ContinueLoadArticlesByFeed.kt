package com.haomins.domain.usecase.article

import com.haomins.domain.exception.ParamsShouldNotBeNullException
import com.haomins.domain.repositories.ArticleListRepositoryContract
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SerializedExecutionOnly
import com.haomins.domain.usecase.SingleUseCase
import com.haomins.model.entity.ArticleEntity
import io.reactivex.Single
import javax.inject.Inject

class ContinueLoadArticlesByFeed @Inject constructor(
    private val articleListRepositoryContract: ArticleListRepositoryContract,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : SingleUseCase<ContinueLoadArticlesByFeed.Companion.Param, List<ArticleEntity>>(
    executionScheduler = executionScheduler,
    postExecutionScheduler = postExecutionScheduler
), SerializedExecutionOnly<List<ArticleEntity>> {

    override var isExecuting = false

    override fun buildUseCaseSingle(params: Param?): Single<List<ArticleEntity>> {
        if (params == null) throw ParamsShouldNotBeNullException()
        return articleListRepositoryContract
            .continueLoadArticleItems(params.feedId)
            .checkForExecuting()
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