package com.haomins.data.repositories

import com.haomins.data.db.dao.ArticleDao
import com.haomins.data.mapper.entitymapper.ArticleEntityMapper
import com.haomins.domain.repositories.ArticleDetailRepositoryContract
import com.haomins.model.entity.ArticleEntity
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleDetailRepository @Inject constructor(
    private val articleDao: ArticleDao,
    private val articleEntityMapper: ArticleEntityMapper
) : ArticleDetailRepositoryContract {

    companion object {
        const val TAG = "ArticleDetailRepository"
    }

    override fun loadArticleDetail(itemId: String): Single<ArticleEntity> {
        return articleDao
            .selectArticleByItemId(itemId)
    }

}