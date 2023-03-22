package com.haomins.domain.repositories

import com.haomins.model.entity.ArticleEntity
import io.reactivex.Single

interface ArticleDetailRepositoryContract {

    fun loadArticleDetail(itemId: String): Single<ArticleEntity>

}