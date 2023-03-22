package com.haomins.domain.usecase.article.local

import com.haomins.domain.repositories.local.ArticleListLocalRepository
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SingleUseCase
import com.haomins.model.entity.ArticleEntity
import io.reactivex.Single
import javax.inject.Inject

class LoadAllArticlesFromLocal @Inject constructor(
    private val articleListLocalRepository: ArticleListLocalRepository,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : SingleUseCase<Unit, List<ArticleEntity>>(
    executionScheduler = executionScheduler,
    postExecutionScheduler = postExecutionScheduler
) {

    override fun buildUseCaseSingle(params: Unit?): Single<List<ArticleEntity>> {
        return articleListLocalRepository
            .loadAllArticles()
    }

}