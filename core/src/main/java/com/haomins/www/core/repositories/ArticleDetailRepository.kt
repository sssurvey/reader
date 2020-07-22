package com.haomins.www.core.repositories

import com.haomins.www.core.data.entities.ArticleEntity
import com.haomins.www.core.policy.RxSchedulingPolicy
import com.haomins.www.core.service.RoomService
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleDetailRepository @Inject constructor(
    private val roomService: RoomService,
    private val defaultSchedulingPolicy: RxSchedulingPolicy
) {

    fun loadArticleDetail(itemId: String): Single<ArticleEntity> {
        with(defaultSchedulingPolicy) {
            return roomService
                .articleDao()
                .selectArticleByItemId(itemId)
                .defaultSchedulingPolicy()
        }
    }

}