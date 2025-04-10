package com.haomins.data.datastore.remote

import com.haomins.data.service.TheOldReaderService
import com.haomins.domain.common.PrefUtils
import com.haomins.domain.repositories.remote.ArticleListRemoteRepository
import com.haomins.model.PreferenceKey
import com.haomins.model.remote.article.ArticleResponseModel
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class ArticleListRemoteDataStore @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val prefUtils: PrefUtils,
) : ArticleListRemoteRepository {

    companion object {
        const val TAG = "ArticleListRemoteDataStore"
    }

    override fun loadAllArticleItemsFromRemote(
        continueId: String
    ): Single<Pair<String, List<ArticleResponseModel>>> {
        return theOldReaderService.loadAllArticles(
            headerAuthValue = getHeaderAuthValue(),
            continueLoad = continueId
        ).flatMap { itemRefListResponse ->

            val newContinueId = itemRefListResponse.continuation ?: ""

            Observable
                .fromIterable(itemRefListResponse.itemRefs)
                .flatMapSingle { itemRef ->
                    theOldReaderService.loadArticleDetailsByRefId(
                        headerAuthValue = getHeaderAuthValue(),
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
            headerAuthValue = getHeaderAuthValue(),
            feedId = feedId,
            continueLoad = continueId
        ).flatMap { itemRefListResponse ->

            val newContinueId = itemRefListResponse.continuation ?: ""

            Observable
                .fromIterable(itemRefListResponse.itemRefs)
                .flatMapSingle { itemRef ->
                    theOldReaderService.loadArticleDetailsByRefId(
                        headerAuthValue = getHeaderAuthValue(),
                        refItemId = itemRef.id
                    )
                }
                .toList()
                .flatMap {
                    Single.just(newContinueId to it)
                }
        }
    }

    // TODO: [ISSUE-226] remove `runBlocking {}`
    private fun getHeaderAuthValue() = runBlocking {
        (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                + prefUtils.getString(PreferenceKey.AUTH_CODE_KEY))
    }

}