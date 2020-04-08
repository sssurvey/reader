package com.haomins.reader.repositories

import com.haomins.www.data.db.AppDatabase
import com.haomins.www.data.db.entities.ArticleEntity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ArticleDetailRepository @Inject constructor(
    private val appDatabase: AppDatabase
) {

    fun loadArticleDetail(itemId: String): Single<ArticleEntity> {
        return appDatabase
            .articleDao()
            .selectArticleByItemId(itemId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}