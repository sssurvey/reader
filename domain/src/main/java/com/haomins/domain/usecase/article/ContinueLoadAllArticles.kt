package com.haomins.domain.usecase.article

import com.haomins.domain.repositories.ArticleListRepositoryContract
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.ObservableUseCase
import io.reactivex.Observable
import javax.inject.Inject

class ContinueLoadAllArticles @Inject constructor(
    private val articleListRepositoryContract: ArticleListRepositoryContract,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : ObservableUseCase<Unit, Unit>(
    executionScheduler = executionScheduler,
    postExecutionScheduler = postExecutionScheduler
) {

    override fun buildUseCaseObservable(params: Unit?): Observable<Unit> {
        return articleListRepositoryContract
            .continueLoadAllArticleItemRefs()
    }

}