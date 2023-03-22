package com.haomins.domain.usecase.article

import com.haomins.domain.repositories.ArticleListRepositoryContract
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SerializedExecutionOnly
import com.haomins.domain.usecase.SingleUseCase
import com.haomins.model.entity.ArticleEntity
import io.reactivex.Single
import javax.inject.Inject

class ContinueLoadAllArticles @Inject constructor(
    private val articleListRepositoryContract: ArticleListRepositoryContract,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : SingleUseCase<Unit, List<ArticleEntity>>(
    executionScheduler = executionScheduler,
    postExecutionScheduler = postExecutionScheduler
), SerializedExecutionOnly<List<ArticleEntity>> {

    override var isExecuting = false

    override fun buildUseCaseSingle(params: Unit?): Single<List<ArticleEntity>> {
        return articleListRepositoryContract
            .continueLoadAllArticleItems()
            .checkForExecuting()
    }
}