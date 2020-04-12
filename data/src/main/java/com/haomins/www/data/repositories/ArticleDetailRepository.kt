package com.haomins.www.data.repositories

import com.haomins.www.data.db.RoomService
import com.haomins.www.data.db.entities.ArticleEntity
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ArticleDetailRepository @Inject constructor(
    private val roomService: RoomService
) {

    fun loadArticleDetail(itemId: String): Single<ArticleEntity> {
        return roomService
            .articleDao()
            .selectArticleByItemId(itemId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}