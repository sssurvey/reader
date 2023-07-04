package com.haomins.domain.repositories.local

import androidx.paging.PagingSource
import com.haomins.model.entity.ArticleEntity
import io.reactivex.Completable

interface ArticleListLocalRepository {

    fun saveAllArticles(articleEntities: List<ArticleEntity>): Completable

    fun loadAllArticles(): PagingSource<Int, ArticleEntity>

    fun loadAllArticlesFromFeed(feedId: String): PagingSource<Int, ArticleEntity>

    fun clearAllArticles(): Completable
}