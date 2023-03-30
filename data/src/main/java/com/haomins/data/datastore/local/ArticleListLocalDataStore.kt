package com.haomins.data.datastore.local

import androidx.paging.PagingSource
import com.haomins.data.db.dao.ArticleDao
import com.haomins.domain.repositories.local.ArticleListLocalRepository
import com.haomins.model.entity.ArticleEntity
import io.reactivex.Completable
import javax.inject.Inject

class ArticleListLocalDataStore @Inject constructor(
    private val articleDao: ArticleDao
) : ArticleListLocalRepository {

    override fun saveAllArticles(articleEntities: List<ArticleEntity>): Completable {
        return articleDao.insert(
            articleEntities
        )
    }

    override fun loadAllArticles(): PagingSource<Int, ArticleEntity> {
        return articleDao.getAll()
    }

    override fun loadAllArticlesFromFeed(feedId: String): PagingSource<Int, ArticleEntity> {
        return articleDao.getAllFromFeed(feedId)
    }
}