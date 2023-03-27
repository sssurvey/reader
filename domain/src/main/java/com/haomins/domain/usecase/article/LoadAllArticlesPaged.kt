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
) : FlowableUseCase<LoadAllArticlesPaged.Companion.Params, PagingData<ArticleEntity>>(
    executionScheduler,
    postExecutionScheduler
) {

    override fun buildUseCaseFlowable(params: Params?): Flowable<PagingData<ArticleEntity>> {
        return if (params == null) {
            articleListPagingRepository.getArticleListStream()
        } else {
            articleListPagingRepository.getArticleListStream(params.feedId)
        }
    }

    companion object {
        data class Params(
            val feedId: String
        )

        fun forLoadAllArticlesPaged(feedId: String): Params {
            return Params(feedId)
        }
    }
}