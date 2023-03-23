package com.haomins.domain.usecase.article.remote

import com.haomins.domain.repositories.remote.ArticleListRemoteRepository
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SingleUseCase
import com.haomins.model.remote.article.ArticleResponseModel
import io.reactivex.Single
import javax.inject.Inject

class ContinueLoadAllArticlesFromRemote @Inject constructor(
    private val articleListRemoteRepository: ArticleListRemoteRepository,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : SingleUseCase<Unit, List<ArticleResponseModel>>(
    executionScheduler = executionScheduler,
    postExecutionScheduler = postExecutionScheduler
) {

    override fun buildUseCaseSingle(params: Unit?): Single<List<ArticleResponseModel>> {
        return articleListRemoteRepository
            .continueLoadAllArticleItems()
    }

}