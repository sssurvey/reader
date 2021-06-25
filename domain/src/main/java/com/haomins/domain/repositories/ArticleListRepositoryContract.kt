package com.haomins.domain.repositories

import com.haomins.domain.model.entities.ArticleEntity
import io.reactivex.Observable

interface ArticleListRepositoryContract {

    fun loadAllArticleItemRefs(): Observable<List<ArticleEntity>>

    fun loadArticleItemRefs(feedId: String): Observable<List<ArticleEntity>>

    fun continueLoadAllArticleItemRefs(): Observable<Unit>

    fun continueLoadArticleItemRefs(feedId: String): Observable<Unit>

}