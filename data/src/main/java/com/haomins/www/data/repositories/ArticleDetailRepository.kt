package com.haomins.www.data.repositories

import android.util.Log
import com.haomins.www.data.model.entities.ArticleEntity
import com.haomins.www.data.service.RoomService
import com.haomins.www.data.strategies.RxSchedulingStrategy
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleDetailRepository @Inject constructor(
        private val roomService: RoomService,
        private val defaultSchedulingStrategy: RxSchedulingStrategy
) {

    companion object {
        const val TAG = "ArticleDetailRepository"
    }

    fun loadArticleDetail(itemId: String): Single<ArticleEntity> {
        with(defaultSchedulingStrategy) {
            return roomService
                    .articleDao()
                    .selectArticleByItemId(itemId)
                    .doOnError { Log.d(TAG, "onError :: ${it.printStackTrace()}") }
                    .useIoThreadsOnly()
        }
    }

}