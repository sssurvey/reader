package com.haomins.domain.repositories

import androidx.paging.PagingData
import com.haomins.model.entity.ArticleEntity
import io.reactivex.Flowable

interface ArticleListPagerRepository {

    fun getArticleListStream(): Flowable<PagingData<ArticleEntity>>

    fun getArticleListStream(feedId: String): Flowable<PagingData<ArticleEntity>>

}