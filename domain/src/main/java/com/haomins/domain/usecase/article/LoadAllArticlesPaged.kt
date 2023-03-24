package com.haomins.domain.usecase.article

import androidx.paging.PagingData
import com.haomins.domain.repositories.ArticleListPagingRepository
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.FlowableUseCase
import com.haomins.model.entity.ArticleEntity
import io.reactivex.Flowable
import javax.inject.Inject

class LoadAllArticlesPaged @Inject constructor(
    private val articleListPagingRepository: ArticleListPagingRepository,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : FlowableUseCase<Unit, PagingData<ArticleEntity>>(
    executionScheduler,
    postExecutionScheduler
) {

    override fun buildUseCaseFlowable(params: Unit?): Flowable<PagingData<ArticleEntity>> {
        return articleListPagingRepository.getArticleListStream()
    }

}