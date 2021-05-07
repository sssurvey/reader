package com.haomins.domain.usecase.articledetails

import com.haomins.domain.exception.ParamsShouldNotBeNullException
import com.haomins.domain.model.entities.ArticleEntity
import com.haomins.domain.repositories.ArticleDetailRepositoryContract
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class LoadArticleData @Inject constructor(
        private val articleDetailRepositoryContract: ArticleDetailRepositoryContract,
        executionScheduler: ExecutionScheduler,
        postExecutionScheduler: PostExecutionScheduler
) : SingleUseCase<LoadArticleData.Companion.Param, ArticleEntity>(
        executionScheduler = executionScheduler,
        postExecutionScheduler = postExecutionScheduler
) {

    override fun buildUseCaseSingle(params: Param?): Single<ArticleEntity> {
        if (params == null) throw ParamsShouldNotBeNullException()
        return articleDetailRepositoryContract
                .loadArticleDetail(params.articleId)
    }

    companion object {

        data class Param(
                val articleId: String
        )

        fun forLoadArticleContent(articleId: String): Param {
            return Param(
                    articleId = articleId
            )
        }

    }

}