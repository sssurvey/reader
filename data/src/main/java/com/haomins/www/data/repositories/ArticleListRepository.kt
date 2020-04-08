package com.haomins.www.data.repositories

import android.content.SharedPreferences
import android.util.Log
import com.haomins.www.data.SharedPreferenceKey
import com.haomins.www.data.service.TheOldReaderService
import com.haomins.www.data.util.getValue
import com.haomins.www.data.db.AppDatabase
import com.haomins.www.data.db.entities.ArticleEntity
import com.haomins.www.data.models.article.ArticleResponseModel
import com.haomins.www.data.models.article.ItemRefListResponseModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ArticleListRepository @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val appDatabase: AppDatabase,
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        const val TAG = "ArticleListRepository"
        private const val MAX_ALLOWED_CONCURRENCY = 5
    }

    private var continueId = ""
    private var isWaitingOnResponse = false
    private val headerAuthValue by lazy {
        (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                + sharedPreferences.getValue(SharedPreferenceKey.AUTH_CODE_KEY))
    }

    fun loadArticleItemRefs(feedId: String): Observable<List<ArticleEntity>> {
        theOldReaderService.loadArticleListByFeed(
            headerAuthValue = headerAuthValue,
            feedId = feedId
        ).subscribe(object : DisposableSingleObserver<ItemRefListResponseModel>() {
            override fun onSuccess(t: ItemRefListResponseModel) {
                continueId = t.continuation
                if (t.itemRefs.isNotEmpty()) loadIndividualArticleInformation(t)
            }
            override fun onError(e: Throwable) { e.printStackTrace() }
        })
        return appDatabase.articleDao().selectAllArticleByFeedId(feedId)
    }

    fun continueLoadArticleItemRefs(feedId: String) {
        if (!isWaitingOnResponse) {
            isWaitingOnResponse = true
            theOldReaderService.loadArticleListByFeed(
                headerAuthValue = headerAuthValue,
                feedId = feedId,
                continueLoad = continueId
            ).subscribe(object : DisposableSingleObserver<ItemRefListResponseModel>() {
                override fun onSuccess(t: ItemRefListResponseModel) {
                    continueId = t.continuation
                    isWaitingOnResponse = false
                    if (t.itemRefs.isNotEmpty()) loadIndividualArticleInformation(t)
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
            appDatabase.articleDao().insert(
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