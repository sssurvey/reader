package com.haomins.data.datastore.remote

import android.content.SharedPreferences
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

    //TODO: 143 double check later
    override fun loadAllArticleItemsFromRemote(continueId: String): Single<List<Pair<String, ArticleResponseModel>>> {
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

}