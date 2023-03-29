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

    private val headerAuthValue by lazy {
        (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                + sharedPreferences.getString(SharedPreferenceKey.AUTH_CODE_KEY))
    }

    //TODO: API Single<Pair<ID, List<Items>>> is better than this
    override fun loadAllArticleItemsFromRemote(continueId: String): Single<List<Pair<String, ArticleResponseModel>>> {
        return theOldReaderService.loadAllArticles(
            headerAuthValue = headerAuthValue,
            continueLoad = continueId
        ).flatMapObservable { itemRefListResponse ->
            Observable
                .fromIterable(itemRefListResponse.itemRefs)
                .flatMapSingle { itemRef ->
                    theOldReaderService.loadArticleDetailsByRefId(
                        headerAuthValue = headerAuthValue,
                        refItemId = itemRef.id
                    ).map {
                        val newContinueId = itemRefListResponse.continuation ?: ""
                        newContinueId to it
                    }
                }
        }
            .toList()
    }

    //TODO: API Single<Pair<ID, List<Items>>> is better than this
    override fun loadAllArticleItemsFromRemoteWithFeed(
        feedId: String,
        continueId: String
    ): Single<List<Pair<String, ArticleResponseModel>>> {
        return theOldReaderService.loadArticleListByFeed(
            headerAuthValue = headerAuthValue,
            feedId = feedId,
            continueLoad = continueId
        ).flatMapObservable { itemRefListResponse ->
            Observable
                .fromIterable(itemRefListResponse.itemRefs)
                .flatMapSingle { itemRef ->
                    theOldReaderService.loadArticleDetailsByRefId(
                        headerAuthValue = headerAuthValue,
                        refItemId = itemRef.id
                    ).map {
                        val newContinueId = itemRefListResponse.continuation ?: ""
                        newContinueId to it
                    }
                }
        }
            .toList()
    }

}