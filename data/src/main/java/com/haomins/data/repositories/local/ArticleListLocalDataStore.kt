package com.haomins.data.repositories.local

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

}