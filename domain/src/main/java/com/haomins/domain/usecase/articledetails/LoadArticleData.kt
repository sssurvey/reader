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

    /**
     * Load article data based on the ID of the article.
     *
     * @param params takes an item ID (article ID)
     * @return a {@code Single<ArticleEntity>} contains the information about the article.
     *
     * @throws ParamsShouldNotBeNullException param must be non-null in this use case
     */
    override fun buildUseCaseSingle(params: Param?): Single<ArticleEntity> {
        if (params == null) throw ParamsShouldNotBeNullException()
        return articleDetailRepositoryContract
            .loadArticleDetail(params.articleId)
    }

    companion object {

        data class Param(
            val articleId: String
        )

        /**
         * Create {@code Param} with {@code article ID} that will be used to load the articles.
         *
         * @param articleId String article ID
         * @return {@code LoadArticleData.Param}
         */
        fun forLoadArticleContent(articleId: String): Param {
            return Param(
                articleId = articleId
            )
        }

    }

}