package com.haomins.domain.usecase.article.remote

import com.haomins.domain.exception.ParamsShouldNotBeNullException
import com.haomins.domain.repositories.ArticleListRepositoryContract
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SingleUseCase
import com.haomins.model.remote.article.ArticleResponseModel
import io.reactivex.Single
import javax.inject.Inject

class LoadAllArticlesByFeedFromRemote @Inject constructor(
    private val articleListRepositoryContract: ArticleListRepositoryContract,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : SingleUseCase<LoadAllArticlesByFeedFromRemote.Companion.Params, List<ArticleResponseModel>>(
    executionScheduler = executionScheduler,
    postExecutionScheduler = postExecutionScheduler
) {

    override fun buildUseCaseSingle(params: Params?): Single<List<ArticleResponseModel>> {
        if (params == null) throw ParamsShouldNotBeNullException()
        return articleListRepositoryContract.loadArticleAllItemsV2(params.feedId)
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