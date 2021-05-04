package com.haomins.www.data.repositories

import android.content.SharedPreferences
import android.util.Log
import com.haomins.www.data.model.SharedPreferenceKey
import com.haomins.www.data.model.entities.ArticleEntity
import com.haomins.www.data.model.responses.article.ArticleResponseModel
import com.haomins.www.data.model.responses.article.ItemRefListResponseModel
import com.haomins.www.data.service.RoomService
import com.haomins.www.data.service.TheOldReaderService
import com.haomins.www.data.strategies.RxSchedulingStrategy
import com.haomins.www.data.util.getString
import io.reactivex.Observable
import io.reactivex.Single
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleListRepository @Inject constructor(
        private val theOldReaderService: TheOldReaderService,
        private val roomService: RoomService,
        private val sharedPreferences: SharedPreferences,
        private val defaultSchedulingStrategy: RxSchedulingStrategy
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
        with(defaultSchedulingStrategy) {
            return theOldReaderService
                    .loadAllArticles(headerAuthValue = headerAuthValue)
                    .doOnError(::onLoadError)
                    .flatMapObservable { loadIndividualArticleInformation(it) }
                    .onErrorReturn { roomService.articleDao().getAll() }
                    .flatMap { roomService.articleDao().getAll() }
                    .debounce(DEFAULT_DEBOUNCE_TIME_IN_MILLISECOND, TimeUnit.MILLISECONDS)
                    .useDefaultSchedulingPolicy()
        }
    }

    fun loadArticleItemRefs(feedId: String): Observable<List<ArticleEntity>> {
        with(defaultSchedulingStrategy) {
            return theOldReaderService
                    .loadArticleListByFeed(headerAuthValue = headerAuthValue, feedId = feedId)
                    .doOnError(::onLoadError)
                    .flatMapObservable { loadIndividualArticleInformation(it) }
                    .onErrorReturn { roomService.articleDao().selectAllArticleByFeedId(feedId) }
                    .flatMap { roomService.articleDao().selectAllArticleByFeedId(feedId) }
                    .debounce(DEFAULT_DEBOUNCE_TIME_IN_MILLISECOND, TimeUnit.MILLISECONDS)
                    .useDefaultSchedulingPolicy()
        }
    }

    fun continueLoadAllArticleItemRefs(): Observable<Unit> {
        with(defaultSchedulingStrategy) {
            return theOldReaderService
                    .loadAllArticles(headerAuthValue = headerAuthValue, continueLoad = continueId)
                    .doOnError(::onLoadError)
                    .doOnSuccess { continueId = it.continuation }
                    .flatMapObservable { loadIndividualArticleInformation(it) }
                    .useDefaultSchedulingPolicy()
        }
    }

    fun continueLoadArticleItemRefs(feedId: String): Observable<Unit> {
        with(defaultSchedulingStrategy) {
            return theOldReaderService
                    .loadArticleListByFeed(
                            headerAuthValue = headerAuthValue,
                            feedId = feedId,
                            continueLoad = continueId
                    )
                    .doOnError(::onLoadError)
                    .doOnSuccess { continueId = it.continuation }
                    .flatMapObservable { loadIndividualArticleInformation(it) }
                    .useDefaultSchedulingPolicy()
        }
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