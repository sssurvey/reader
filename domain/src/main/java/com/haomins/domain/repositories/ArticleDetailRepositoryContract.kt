package com.haomins.domain.repositories

import com.haomins.domain.model.entities.ArticleEntity
import io.reactivex.Single

interface ArticleDetailRepositoryContract {

    fun loadArticleDetail(itemId: String): Single<ArticleEntity>

}