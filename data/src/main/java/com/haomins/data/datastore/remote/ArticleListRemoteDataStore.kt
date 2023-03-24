package com.haomins.data.datastore.remote

import android.content.SharedPreferences
import android.util.Log
import com.haomins.data.service.TheOldReaderService
import com.haomins.data.util.getString
import com.haomins.domain.repositories.remote.ArticleListRemoteRepository
import com.haomins.model.SharedPreferenceKey
import com.haomins.model.remote.article.ArticleResponseModel
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class ArticleListRemoteDataStore @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val sharedPreferences: SharedPreferences,
) : ArticleListRemoteRepository {

    companion object {
        const val TAG = "ArticleListRemoteDataStore"
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

    override fun loadArticleAllItems(feedId: String): Single<List<ArticleResponseModel>> {
        return loadAllArticleItemsFromRemoteByFeedId(feedId)
    }

    override fun continueLoadAllArticleItems(feedId: String): Single<List<ArticleResponseModel>> {
        return loadAllArticleItemsFromRemoteByFeedId(feedId, true)
    }

    //TODO: 143 double check later
    fun loadAllArticleItemsFromRemote(continueId: String): Single<List<Pair<String, ArticleResponseModel>>> {
        return theOldReaderService.loadAllArticles(
            headerAuthValue = headerAuthValue,
            continueLoad = continueId
        ).flatMapObservable { itemRefListResponse ->
            Observable
                .fromIterable(itemRefListResponse.itemRefs)
                .flatMapSingle { itemRef ->
                    //TODO: 143 TO TAXING on the SYSTEM??? consider single thread for this?
                    theOldReaderService.loadArticleDetailsByRefId(
                        headerAuthValue = headerAuthValue,
                        refItemId = itemRef.id
                    ).map {
                        itemRefListResponse.continuation to it
                    }
                }
        }
            .toList()
    }

    private fun loadAllArticleItemsFromRemoteByFeedId(
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

    private fun onLoadError(e: Throwable) {
        Log.d(TAG, "onError :: ${e.printStackTrace()}")
    }

}