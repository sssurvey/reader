package com.haomins.www.core.repositories

import com.haomins.www.core.data.entities.ArticleEntity
import com.haomins.www.core.strategies.RxSchedulingStrategy
import com.haomins.www.core.service.RoomService
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