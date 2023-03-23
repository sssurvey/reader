package com.haomins.data.datastore.local

import com.haomins.data.db.dao.ArticleDao
import com.haomins.domain.repositories.local.ArticleDetailLocalRepository
import com.haomins.model.entity.ArticleEntity
import io.reactivex.Single
import javax.inject.Inject

class ArticleDetailLocalDataStore @Inject constructor(
    private val articleDao: ArticleDao,
) : ArticleDetailLocalRepository {

    companion object {
        const val TAG = "ArticleDetailLocalDataStore"
    }

    override fun loadArticleDetail(itemId: String): Single<ArticleEntity> {
        return articleDao
            .selectArticleByItemId(itemId)
    }

}