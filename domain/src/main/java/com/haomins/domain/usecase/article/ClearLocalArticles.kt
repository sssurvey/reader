package com.haomins.domain.usecase.article

import com.haomins.domain.repositories.local.ArticleListLocalRepository
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.CompletableUseCase
import io.reactivex.Completable
import javax.inject.Inject

class ClearLocalArticles @Inject constructor(
    private val articleListLocalRepository: ArticleListLocalRepository,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : CompletableUseCase<Unit>(
    executionScheduler,
    postExecutionScheduler
) {

    override fun buildUseCaseCompletable(params: Unit?): Completable {
        return articleListLocalRepository.clearAllArticles()
    }

}