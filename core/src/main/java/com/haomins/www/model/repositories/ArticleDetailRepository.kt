package com.haomins.www.model.repositories

import com.haomins.www.model.data.entities.ArticleEntity
import com.haomins.www.model.strategies.RxSchedulingStrategy
import com.haomins.www.model.service.RoomService
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleDetailRepository @Inject constructor(
    private val roomService: RoomService,
    private val defaultSchedulingStrategy: RxSchedulingStrategy
) {

    fun loadArticleDetail(itemId: String): Single<ArticleEntity> {
        with(defaultSchedulingStrategy) {
            return roomService
                .articleDao()
                .selectArticleByItemId(itemId)
                .useDefaultSchedulingPolicy()
        }
    }

}