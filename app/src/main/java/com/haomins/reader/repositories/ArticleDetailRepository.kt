package com.haomins.reader.repositories

import com.haomins.reader.data.AppDatabase
import javax.inject.Inject

class ArticleDetailRepository @Inject constructor(
    private val appDatabase: AppDatabase
) {

    fun loadArticleDetail(itemId: String) {

    }

}