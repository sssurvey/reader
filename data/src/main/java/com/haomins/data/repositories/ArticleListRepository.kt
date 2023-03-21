package com.haomins.data.repositories

import android.content.SharedPreferences
import android.util.Log
import com.haomins.data.db.dao.ArticleDao
import com.haomins.data.mapper.entitymapper.ArticleEntityMapper
import com.haomins.data_model.SharedPreferenceKey
import com.haomins.data_model.remote.article.ArticleResponseModel
import com.haomins.data.service.TheOldReaderService
import com.haomins.data.util.extractImageFromImgTags
import com.haomins.data.util.getString
import com.haomins.domain.model.entities.ArticleEntity
import com.haomins.domain.repositories.ArticleListRepositoryContract
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleListRepository @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val articleDao: ArticleDao,
    private val sharedPreferences: SharedPreferences,
    private val articleEntityMapper: ArticleEntityMapper
) : ArticleListRepositoryContract {

    companion object {
        const val TAG = "ArticleListRepository"
    }

    private var continueId = ""
    private val headerAuthValue by lazy {
        (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                + sharedPreferences.getString(SharedPreferenceKey.AUTH_CODE_KEY))
    }

    override fun loadAllArticleItems(): Single<List<ArticleEntity>> {
        return loadAllArticleItemsFromRemote()
    }

    override fun continueLoadAllArticleItems(): Single<List<ArticleEntity>> {
        return loadAllArticleItemsFromRemote(true)
    }

    override fun loadArticleItems(feedId: String): Single<List<ArticleEntity>> {
        return loadAllArticleItemsFromRemoteByFeedId(feedId)
    }

    override fun continueLoadArticleItems(feedId: String): Single<List<ArticleEntity>> {
        return loadAllArticleItemsFromRemoteByFeedId(feedId, true)
    }

    private fun loadAllArticleItemsFromRemoteByFeedId(
        feedId: String,
        continueLoad: Boolean = false
    ): Single<List<ArticleEntity>> {
        return if (continueLoad) {
            theOldReaderService.loadArticleListByFeed(
                headerAuthValue = headerAuthValue,
                feedId = feedId,
                continueLoad = continueId
            ).doOnSuccess {
                continueId = it.continuation
            }
        } else {
            theOldReaderService.loadArticleListByFeed(
                headerAuthValue = headerAuthValue,
                feedId = feedId
            )
        }
            .doOnError(::onLoadError)
            .flatMapObservable {
                Observable
                    .fromIterable(it.itemRefs)
                    .flatMapSingle { itemRef ->
                        theOldReaderService.loadArticleDetailsByRefId(
                            headerAuthValue = headerAuthValue,
                            refItemId = itemRef.id
                        )
                    }
            }
            .toList()
            .map {
                val articleEntities =
                    it.map { articleResponseModel -> articleResponseModel.toArticleEntity() }
                articleDao.insert(*articleEntities.toTypedArray())
                articleEntities
            }.onErrorResumeNext { articleDao.selectAllArticleByFeedId(feedId) }
            .map {
                it.map { articleEntity ->
                    articleEntityMapper.dataModelToDomainModel(
                        articleEntity
                    )
                }
            }
    }

    private fun loadAllArticleItemsFromRemote(continueLoad: Boolean = false): Single<List<ArticleEntity>> {
        return if (continueLoad) {
            theOldReaderService.loadAllArticles(
                headerAuthValue = headerAuthValue,
                continueLoad = continueId
            ).doOnSuccess {
                continueId = it.continuation
            }
        } else {
            theOldReaderService.loadAllArticles(headerAuthValue = headerAuthValue)
        }
            .doOnError(::onLoadError)
            .flatMapObservable {
                Observable
                    .fromIterable(it.itemRefs)
                    .flatMapSingle { itemRef ->
                        theOldReaderService.loadArticleDetailsByRefId(
                            headerAuthValue = headerAuthValue,
                            refItemId = itemRef.id
                        )
                    }
            }
            .toList()
            .map {
                val articleEntities =
                    it.map { articleResponseModel -> articleResponseModel.toArticleEntity() }
                articleDao.insert(*articleEntities.toTypedArray())
                articleEntities
            }.onErrorResumeNext { articleDao.getAll() }
            .map {
                it.map { articleEntity -> articleEntityMapper.dataModelToDomainModel(articleEntity) }
            }
    }

    private fun ArticleResponseModel.toArticleEntity(): com.haomins.data_model.entity.ArticleEntity {
        return com.haomins.data_model.entity.ArticleEntity(
            itemId = items.first().id,
            itemTitle = items.first().title,
            itemUpdatedMillisecond = items.first().updatedMillisecond,
            itemPublishedMillisecond = items.first().publishedMillisecond,
            author = items.first().author,
            content = items.first().summary.content,
            feedId = id,
            href = alternate.herf,
            previewImageUrl = extractImageFromImgTags(rawHtmlString = items.first().summary.content)
        )
    }

    private fun onLoadError(e: Throwable) {
        Log.d(TAG, "onError :: ${e.printStackTrace()}")
    }

}