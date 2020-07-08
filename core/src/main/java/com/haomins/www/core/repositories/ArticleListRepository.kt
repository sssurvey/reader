package com.haomins.www.core.repositories

import android.content.SharedPreferences
import android.util.Log
import com.haomins.www.core.data.SharedPreferenceKey
import com.haomins.www.core.data.entities.ArticleEntity
import com.haomins.www.core.data.models.article.ArticleResponseModel
import com.haomins.www.core.data.models.article.ItemRefListResponseModel
import com.haomins.www.core.service.RoomService
import com.haomins.www.core.service.TheOldReaderService
import com.haomins.www.core.util.defaultSchedulingPolicy
import com.haomins.www.core.util.getString
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleListRepository @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val roomService: RoomService,
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        const val TAG = "ArticleListRepository"
        private const val DEFAULT_DEBOUNCE_TIME_IN_MILLISECOND = 500L
    }

    private var continueId = ""
    private val headerAuthValue by lazy {
        (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                + sharedPreferences.getString(SharedPreferenceKey.AUTH_CODE_KEY))
    }

    fun loadAllArticleItemRefs(): Observable<List<ArticleEntity>> {
        return theOldReaderService
            .loadAllArticles(headerAuthValue = headerAuthValue)
            .doOnError(::onLoadError)
            .flatMapObservable { loadIndividualArticleInformation(it) }
            .onErrorReturn { roomService.articleDao().getAll() }
            .flatMap { roomService.articleDao().getAll() }
            .debounce(DEFAULT_DEBOUNCE_TIME_IN_MILLISECOND, TimeUnit.MILLISECONDS)
            .defaultSchedulingPolicy()
    }

    fun loadArticleItemRefs(feedId: String): Observable<List<ArticleEntity>> {
        return theOldReaderService
            .loadArticleListByFeed(headerAuthValue = headerAuthValue, feedId = feedId)
            .doOnError(::onLoadError)
            .flatMapObservable { loadIndividualArticleInformation(it) }
            .onErrorReturn { roomService.articleDao().selectAllArticleByFeedId(feedId) }
            .flatMap { roomService.articleDao().selectAllArticleByFeedId(feedId) }
            .debounce(DEFAULT_DEBOUNCE_TIME_IN_MILLISECOND, TimeUnit.MILLISECONDS)
            .defaultSchedulingPolicy()
    }

    fun continueLoadAllArticleItemRefs(): Observable<Unit> {
        return theOldReaderService
            .loadAllArticles(headerAuthValue = headerAuthValue, continueLoad = continueId)
            .doOnError(::onLoadError)
            .doOnSuccess { continueId = it.continuation }
            .flatMapObservable { loadIndividualArticleInformation(it) }
            .defaultSchedulingPolicy()
    }

    fun continueLoadArticleItemRefs(feedId: String): Observable<Unit> {
        return theOldReaderService
            .loadArticleListByFeed(
                headerAuthValue = headerAuthValue,
                feedId = feedId,
                continueLoad = continueId
            )
            .doOnError(::onLoadError)
            .doOnSuccess { continueId = it.continuation }
            .flatMapObservable { loadIndividualArticleInformation(it) }
            .defaultSchedulingPolicy()
    }

    private fun loadIndividualArticleInformation(itemRefListResponseModel: ItemRefListResponseModel)
            : Observable<Unit> {
        return Observable
            .fromIterable(itemRefListResponseModel.itemRefs)
            .flatMapSingle {
                theOldReaderService.loadArticleDetailsByRefId(
                    headerAuthValue = headerAuthValue,
                    refItemId = it.id
                )
            }
            .flatMapSingle { saveIndividualArticleToDatabase(it) }
    }

    private fun saveIndividualArticleToDatabase(articleResponseModel: ArticleResponseModel)
            : Single<Unit> {
        return Single.fromCallable {
            roomService.articleDao().insert(
                ArticleEntity(
                    itemId = articleResponseModel.items.first().id,
                    itemTitle = articleResponseModel.items.first().title,
                    itemUpdatedMillisecond = articleResponseModel.items.first().updatedMillisecond,
                    itemPublishedMillisecond = articleResponseModel.items.first().publishedMillisecond,
                    author = articleResponseModel.items.first().author,
                    content = articleResponseModel.items.first().summary.content,
                    feedId = articleResponseModel.id
                )
            )
        }
    }

    private fun onLoadError(e: Throwable) {
        Log.d(TAG, "onError :: ${e.printStackTrace()}")
    }

}