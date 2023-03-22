package com.haomins.data.repositories.local

import com.haomins.data.db.dao.ArticleDao
import com.haomins.domain.repositories.ArticleDetailLocalRepository
import com.haomins.model.entity.ArticleEntity
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleDetailLocalDataStore @Inject constructor(
    private val articleDao: ArticleDao,
) : ArticleDetailLocalRepository {

    companion object {
        const val TAG = "ArticleDetailRepository"
    }

    override fun loadArticleDetail(itemId: String): Single<ArticleEntity> {
        return articleDao
            .selectArticleByItemId(itemId)
    }

}