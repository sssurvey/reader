package com.haomins.domain.repositories

import com.haomins.domain.model.entities.ArticleEntity
import io.reactivex.Observable
import io.reactivex.Single

interface ArticleListRepositoryContract {

    fun loadArticleItemRefs(feedId: String): Observable<List<ArticleEntity>>

    fun continueLoadArticleItemRefs(feedId: String): Observable<Unit>

    fun continueLoadAllArticleItems(): Single<List<ArticleEntity>>

    fun loadAllArticleItems(): Single<List<ArticleEntity>>
}