package com.haomins.data.repositories

import android.content.SharedPreferences
import android.util.Log
import com.haomins.data.db.dao.ArticleDao
import com.haomins.data.service.TheOldReaderService
import com.haomins.data.util.getString
import com.haomins.domain.common.HtmlUtil
import com.haomins.domain.repositories.ArticleListRepositoryContract
import com.haomins.model.SharedPreferenceKey
import com.haomins.model.entity.ArticleEntity
import com.haomins.model.remote.article.ArticleResponseModel
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class ArticleListRepository @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val articleDao: ArticleDao,
    private val sharedPreferences: SharedPreferences,
    private val htmlUtil: HtmlUtil
) : ArticleListRepositoryContract {

    companion object {
        const val TAG = "ArticleListRepository"
    }

    private var continueId = ""
    private val headerAuthValue by lazy {
        (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                + sharedPreferences.getString(SharedPreferenceKey.AUTH_CODE_KEY))
    }

    override fun loadAllArticleItems(): Single<List<ArticleResponseModel>> {
        return loadAllArticleItemsFromRemote()
    }

    override fun continueLoadAllArticleItems(): Single<List<ArticleResponseModel>> {
        return loadAllArticleItemsFromRemote(true)
    }

    //TODO: new
    override fun loadArticleAllItemsV2(feedId: String): Single<List<ArticleResponseModel>> {
        return loadAllArticleItemsFromRemoteByFeedIdV2(feedId)
    }

    //TODO: new
    override fun continueLoadAllArticleItemsV2(feedId: String): Single<List<ArticleResponseModel>> {
        return loadAllArticleItemsFromRemoteByFeedIdV2(feedId, true)
    }

    @Deprecated(message = "TO BE replaced with V2", replaceWith = ReplaceWith(""))
    override fun loadArticleItems(feedId: String): Single<List<ArticleEntity>> {
        return loadAllArticleItemsFromRemoteByFeedId(feedId)
    }

    @Deprecated(message = "TO BE replaced with V2", replaceWith = ReplaceWith(""))
    override fun continueLoadArticleItems(feedId: String): Single<List<ArticleEntity>> {
        return loadAllArticleItemsFromRemoteByFeedId(feedId, true)
    }

    @Deprecated(message = "TO BE replaced with V2", replaceWith = ReplaceWith(""))
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
    }

    private fun loadAllArticleItemsFromRemoteByFeedIdV2(
        feedId: String,
        continueLoad: Boolean = false
    ): Single<List<ArticleResponseModel>> {
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
    }

    private fun loadAllArticleItemsFromRemote(continueLoad: Boolean = false): Single<List<ArticleResponseModel>> {
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
    }

    private fun ArticleResponseModel.toArticleEntity(): ArticleEntity {
        return ArticleEntity(
            itemId = items.first().id,
            itemTitle = items.first().title,
            itemUpdatedMillisecond = items.first().updatedMillisecond,
            itemPublishedMillisecond = items.first().publishedMillisecond,
            author = items.first().author,
            content = items.first().summary.content,
            feedId = id,
            href = alternate.herf,
            previewImageUrl = htmlUtil.extractImageFromImgTags(rawHtmlString = items.first().summary.content)
        )
    }

    private fun onLoadError(e: Throwable) {
        Log.d(TAG, "onError :: ${e.printStackTrace()}")
    }

}