package com.haomins.domain.repositories.local

import com.haomins.model.entity.ArticleEntity
import io.reactivex.Completable
import io.reactivex.Single

interface ArticleListLocalRepository {

    fun loadAllArticles(): Single<List<ArticleEntity>>

    fun loadArticlesFromFeed(feedId: String): Single<List<ArticleEntity>>

    fun saveAllArticles(articleEntities: List<ArticleEntity>): Completable

}