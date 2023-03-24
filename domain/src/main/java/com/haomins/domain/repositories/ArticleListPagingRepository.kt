package com.haomins.domain.repositories

import androidx.paging.PagingData
import com.haomins.model.entity.ArticleEntity
import io.reactivex.Flowable

interface ArticleListPagingRepository {

    fun getArticleListStream(): Flowable<PagingData<ArticleEntity>>

}