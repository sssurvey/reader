package com.haomins.domain.usecase.article

import com.haomins.domain.exception.ParamsShouldNotBeNullException
import com.haomins.domain.repositories.local.ArticleListLocalRepository
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SingleUseCase
import com.haomins.model.entity.ArticleEntity
import io.reactivex.Single
import javax.inject.Inject

class LoadAllArticlesByFeedFromLocal @Inject constructor(
    private val articleListLocalRepository: ArticleListLocalRepository,
    executionException: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) :
    SingleUseCase<LoadAllArticlesByFeedFromLocal.Companion.Params, List<ArticleEntity>>(
        executionException,
        postExecutionScheduler
    ) {

    override fun buildUseCaseSingle(params: Params?): Single<List<ArticleEntity>> {
        if (params == null) throw ParamsShouldNotBeNullException()
        return articleListLocalRepository.loadArticlesFromFeed(params.feedId)
    }

    companion object {

        data class Params(
            val feedId: String
        )

        fun forLoadAllArticlesByFeedFromLocal(feedId: String): Params {
            return Params(feedId)
        }
    }

}