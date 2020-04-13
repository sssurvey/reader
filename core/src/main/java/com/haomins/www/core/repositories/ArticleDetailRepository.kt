package com.haomins.www.core.repositories

import com.haomins.www.core.service.RoomService
import com.haomins.www.core.data.entities.ArticleEntity
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