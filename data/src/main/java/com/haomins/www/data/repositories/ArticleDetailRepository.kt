package com.haomins.www.data.repositories

import android.util.Log
import com.haomins.domain.model.entities.ArticleEntity
import com.haomins.domain.repositories.ArticleDetailRepositoryContract
import com.haomins.www.data.mapper.ArticleEntityMapper
import com.haomins.www.data.service.RoomService
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleDetailRepository @Inject constructor(
        private val roomService: RoomService,
        private val articleEntityMapper: ArticleEntityMapper
) : ArticleDetailRepositoryContract {

    companion object {
        const val TAG = "ArticleDetailRepository"
    }

    override fun loadArticleDetail(itemId: String): Single<ArticleEntity> {
        return roomService
                .articleDao()
                .selectArticleByItemId(itemId)
                .doOnError { Log.d(TAG, "onError :: ${it.printStackTrace()}") }
                .map {
                    articleEntityMapper.dataModelToDomainModel(it)
                }
    }

}