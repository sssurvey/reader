package com.haomins.domain.usecase.article

import com.haomins.domain.model.entities.ArticleEntity
import com.haomins.domain.repositories.ArticleListRepositoryContract
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.ObservableUseCase
import io.reactivex.Observable
import javax.inject.Inject

class LoadAllArticles @Inject constructor(
    private val articleListRepositoryContract: ArticleListRepositoryContract,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : ObservableUseCase<Unit, List<ArticleEntity>>(
    executionScheduler = executionScheduler,
    postExecutionScheduler = postExecutionScheduler
) {

    override fun buildUseCaseObservable(params: Unit?): Observable<List<ArticleEntity>> {
        return articleListRepositoryContract
            .loadAllArticleItemRefs()
    }

}