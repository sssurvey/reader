package com.haomins.domain.usecase.article.remote

import com.haomins.domain.exception.ParamsShouldNotBeNullException
import com.haomins.domain.repositories.remote.ArticleListRemoteRepository
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SingleUseCase
import com.haomins.model.remote.article.ArticleResponseModel
import io.reactivex.Single
import javax.inject.Inject

class LoadAllArticlesByFeedFromRemote @Inject constructor(
    private val articleListRemoteRepository: ArticleListRemoteRepository,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : SingleUseCase<LoadAllArticlesByFeedFromRemote.Companion.Params, List<ArticleResponseModel>>(
    executionScheduler = executionScheduler,
    postExecutionScheduler = postExecutionScheduler
) {

    override fun buildUseCaseSingle(params: Params?): Single<List<ArticleResponseModel>> {
        if (params == null) throw ParamsShouldNotBeNullException()
        return articleListRemoteRepository.loadArticleAllItems(params.feedId)
    }

    companion object {

        data class Params(
            val feedId: String
        )

        fun forLoadAllArticlesByFeedFromRemote(feedId: String): Params {
            return Params(feedId)
        }
    }

}