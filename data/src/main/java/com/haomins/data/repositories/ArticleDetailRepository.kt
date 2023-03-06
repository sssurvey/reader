package com.haomins.data.repositories

import com.haomins.data.mapper.entitymapper.ArticleEntityMapper
import com.haomins.data.service.RoomService
import com.haomins.domain.model.entities.ArticleEntity
import com.haomins.domain.repositories.ArticleDetailRepositoryContract
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
            .map {
                articleEntityMapper.dataModelToDomainModel(it)
            }
    }

}