package com.haomins.data.datastore.local

import androidx.paging.PagingSource
import com.haomins.data.db.dao.ArticleDao
import com.haomins.domain.repositories.local.ArticleListLocalRepository
import com.haomins.model.entity.ArticleEntity
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class ArticleListLocalDataStore @Inject constructor(
    private val articleDao: ArticleDao
) : ArticleListLocalRepository {

    override fun saveAllArticles(articleEntities: List<ArticleEntity>): Completable {
        return articleDao.insertV2(
            articleEntities
        )
    }

    override fun loadArticlesFromFeed(feedId: String): Single<List<ArticleEntity>> {
        return articleDao
            .selectAllArticleByFeedId(feedId)
    }

    override fun loadAllArticles(): Single<List<ArticleEntity>> {
        return articleDao.getAll()
    }

    override fun loadAllArticlesV2(): PagingSource<Int, ArticleEntity> {
        return articleDao.getAllV2()
    }

    override fun loadAllArticlesV2WithFeed(feedId: String): PagingSource<Int, ArticleEntity> {
        return articleDao.getAllV2WithFeed(feedId)
    }
}