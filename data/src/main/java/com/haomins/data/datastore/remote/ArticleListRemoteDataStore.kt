package com.haomins.data.datastore.remote

import com.haomins.data.service.TheOldReaderService
import com.haomins.domain.common.SharedPrefUtils
import com.haomins.domain.repositories.remote.ArticleListRemoteRepository
import com.haomins.model.SharedPreferenceKey
import com.haomins.model.remote.article.ArticleResponseModel
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class ArticleListRemoteDataStore @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val sharedPrefUtils: SharedPrefUtils,
) : ArticleListRemoteRepository {

    companion object {
        const val TAG = "ArticleListRemoteDataStore"
    }

    private val headerAuthValue by lazy {
        (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                + sharedPrefUtils.getString(SharedPreferenceKey.AUTH_CODE_KEY))
    }

    override fun loadAllArticleItemsFromRemote(
        continueId: String
    ): Single<Pair<String, List<ArticleResponseModel>>> {
        return theOldReaderService.loadAllArticles(
            headerAuthValue = headerAuthValue,
            continueLoad = continueId
        ).flatMap { itemRefListResponse ->

            val newContinueId = itemRefListResponse.continuation ?: ""

            Observable
                .fromIterable(itemRefListResponse.itemRefs)
                .flatMapSingle { itemRef ->
                    theOldReaderService.loadArticleDetailsByRefId(
                        headerAuthValue = headerAuthValue,
                        refItemId = itemRef.id
                    )
                }
                .toList()
                .flatMap {
                    Single.just(newContinueId to it)
                }
        }
    }

    override fun loadAllArticleItemsFromRemoteWithFeed(
        feedId: String,
        continueId: String
    ): Single<Pair<String, List<ArticleResponseModel>>> {
        return theOldReaderService.loadArticleListByFeed(
            headerAuthValue = headerAuthValue,
            feedId = feedId,
            continueLoad = continueId
        ).flatMap { itemRefListResponse ->

            val newContinueId = itemRefListResponse.continuation ?: ""

            Observable
                .fromIterable(itemRefListResponse.itemRefs)
                .flatMapSingle { itemRef ->
                    theOldReaderService.loadArticleDetailsByRefId(
                        headerAuthValue = headerAuthValue,
                        refItemId = itemRef.id
                    )
                }
                .toList()
                .flatMap {
                    Single.just(newContinueId to it)
                }
        }
    }

}