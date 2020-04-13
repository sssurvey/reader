package com.haomins.www.core.repositories

import android.content.SharedPreferences
import android.util.Log
import com.haomins.www.core.data.SharedPreferenceKey
import com.haomins.www.core.service.RoomService
import com.haomins.www.core.data.entities.ArticleEntity
import com.haomins.www.core.data.models.article.ArticleResponseModel
import com.haomins.www.core.data.models.article.ItemRefListResponseModel
import com.haomins.www.core.service.TheOldReaderService
import com.haomins.www.core.util.getValue
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ArticleListRepository @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val roomService: RoomService,
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        const val TAG = "ArticleListRepository"
        private const val MAX_ALLOWED_CONCURRENCY = 3
    }

    private var continueId = ""
    private var isWaitingOnResponse = false
    private val headerAuthValue by lazy {
        (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                + sharedPreferences.getValue(SharedPreferenceKey.AUTH_CODE_KEY))
    }

    fun loadAllArticleItemRefs(): Observable<List<ArticleEntity>> {
        theOldReaderService.loadAllArticles(headerAuthValue = headerAuthValue)
            .subscribe(object : DisposableSingleObserver<ItemRefListResponseModel>() {
                override fun onSuccess(t: ItemRefListResponseModel) {
                    continueId = t.continuation
                    if (!t.itemRefs.isNullOrEmpty()) loadIndividualArticleInformation(t)
                }
                override fun onError(e: Throwable) { e.printStackTrace() }
            })
        return roomService.articleDao().getAll()
    }

    fun loadArticleItemRefs(feedId: String): Observable<List<ArticleEntity>> {
        theOldReaderService.loadArticleListByFeed(
            headerAuthValue = headerAuthValue,
            feedId = feedId
        ).subscribe(object : DisposableSingleObserver<ItemRefListResponseModel>() {
            override fun onSuccess(t: ItemRefListResponseModel) {
                continueId = t.continuation
                if (!t.itemRefs.isNullOrEmpty()) loadIndividualArticleInformation(t)
            }
            override fun onError(e: Throwable) { e.printStackTrace() }
        })
        return roomService.articleDao().selectAllArticleByFeedId(feedId)
    }

    fun continueLoadAllArticleItemRefs(doAfterSuccess: () -> Unit) {
        if (!isWaitingOnResponse) {
            isWaitingOnResponse = true
            theOldReaderService.loadAllArticles(
                headerAuthValue = headerAuthValue,
                continueLoad = continueId
            ).doAfterSuccess{
                doAfterSuccess()
            }.subscribe(object : DisposableSingleObserver<ItemRefListResponseModel>() {
                override fun onSuccess(t: ItemRefListResponseModel) {
                    continueId = t.continuation
                    isWaitingOnResponse = false
                    if (!t.itemRefs.isNullOrEmpty()) loadIndividualArticleInformation(t)
                }
                override fun onError(e: Throwable) { e.printStackTrace() }
            })
        }
    }

    fun continueLoadArticleItemRefs(feedId: String, doAfterSuccess: () -> Unit) {
        if (!isWaitingOnResponse) {
            isWaitingOnResponse = true
            theOldReaderService.loadArticleListByFeed(
                headerAuthValue = headerAuthValue,
                feedId = feedId,
                continueLoad = continueId
            ).doAfterSuccess{
                doAfterSuccess()
            }.subscribe(object : DisposableSingleObserver<ItemRefListResponseModel>() {
                override fun onSuccess(t: ItemRefListResponseModel) {
                    continueId = t.continuation
                    isWaitingOnResponse = false
                    if (!t.itemRefs.isNullOrEmpty()) loadIndividualArticleInformation(t)
                }
                override fun onError(e: Throwable) { e.printStackTrace() }
            })
        }
    }

    private fun loadIndividualArticleInformation(itemRefListResponseModel: ItemRefListResponseModel) {
        Observable.fromIterable(itemRefListResponseModel.itemRefs).flatMap({
            theOldReaderService.loadArticleDetailsByRefId(
                headerAuthValue = headerAuthValue,
                refItemId = it.id
            ).toObservable()
        },
            MAX_ALLOWED_CONCURRENCY
        )
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(ArticleResponseModelObserver())
    }

    private fun saveIndividualArticleToDatabase(articleResponseModel: ArticleResponseModel) {
        Completable.fromAction {
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
        }.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()).subscribe()
    }

    private inner class ArticleResponseModelObserver : DisposableObserver<ArticleResponseModel>() {
        override fun onError(e: Throwable) { Log.d(TAG, "onError::${e.printStackTrace()}") }
        override fun onComplete() { Log.d(TAG, "onComplete::called") }
        override fun onNext(t: ArticleResponseModel) {
            saveIndividualArticleToDatabase(t)
            onComplete()
        }
    }

}