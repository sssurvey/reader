package com.haomins.domain.usecase.article.local

import com.haomins.domain.exception.ParamsShouldNotBeNullException
import com.haomins.domain.repositories.local.ArticleListLocalRepository
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.CompletableUseCase
import com.haomins.model.entity.ArticleEntity
import io.reactivex.Completable
import javax.inject.Inject

class SaveAllArticlesToLocal @Inject constructor(
    private val articleListLocalRepository: ArticleListLocalRepository,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : CompletableUseCase<SaveAllArticlesToLocal.Companion.Params>(
    executionScheduler,
    postExecutionScheduler
) {

    override fun buildUseCaseCompletable(params: Params?): Completable {
        if (params == null) throw ParamsShouldNotBeNullException()
        return articleListLocalRepository
            .saveAllArticles(
                params.articleEntities
            )
    }

    companion object {

        data class Params(
            val articleEntities: List<ArticleEntity>
        )

        fun forSaveAllArticlesToLocal(articleEntities: List<ArticleEntity>): Params {
            return Params(articleEntities)
        }
    }

}