package com.haomins.reader.repositories

import android.content.SharedPreferences
import android.util.Log
import com.haomins.reader.TheOldReaderService
import com.haomins.reader.data.AppDatabase
import com.haomins.reader.data.entities.ArticleEntity
import com.haomins.reader.models.article.ArticleResponseModel
import com.haomins.reader.models.article.ItemRefListResponseModel
import com.haomins.reader.utils.getValue
import com.haomins.reader.viewModels.LoginViewModel
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
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

    fun loadArticleItemRefs(feedId: String): Observable<List<ArticleEntity>> {
        theOldReaderService.loadArticleListByFeed(
            headerAuthValue = (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                    + sharedPreferences.getValue(LoginViewModel.AUTH_CODE_KEY)),
            feedId = feedId
        ).subscribe(object : DisposableSingleObserver<ItemRefListResponseModel>() {
            override fun onSuccess(t: ItemRefListResponseModel) {
                continueId = t.continuation
                if (t.itemRefs.isNotEmpty()) loadIndividualArticleInformation(t)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }
        })
        return appDatabase.articleDao().selectAllArticleByFeedId(feedId)
    }

    fun continueLoadArticleItemRefs(feedId: String) {
        //TODO: the flag for avoid executing multiple times is a bad solution, fix it
        if (!isWaitingOnResponse) {
            isWaitingOnResponse = true
            theOldReaderService.loadArticleListByFeed(
                headerAuthValue = (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                        + sharedPreferences.getValue(LoginViewModel.AUTH_CODE_KEY)),
                feedId = feedId,
                continueLoad = continueId
            ).subscribe(object : DisposableSingleObserver<ItemRefListResponseModel>() {
                override fun onSuccess(t: ItemRefListResponseModel) {
                    continueId = t.continuation
                    isWaitingOnResponse = false
                    if (t.itemRefs.isNotEmpty()) loadIndividualArticleInformation(t)
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })
        }

    }

    private fun loadIndividualArticleInformation(itemRefListResponseModel: ItemRefListResponseModel) {
        Observable.fromIterable(itemRefListResponseModel.itemRefs).flatMap({
            theOldReaderService.loadArticleDetailsByRefId(
                headerAuthValue = (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                        + sharedPreferences.getValue(LoginViewModel.AUTH_CODE_KEY)),
                refItemId = it.id
            ).toObservable()
        }, MAX_ALLOWED_CONCURRENCY).subscribe(ArticleResponseModelObserver())
    }

    private fun saveIndividualArticleToDatabase(articleResponseModel: ArticleResponseModel) {
        val articleEntity = ArticleEntity(
            itemId = articleResponseModel.items.first().id,
            itemTitle = articleResponseModel.items.first().title,
            itemUpdatedMillisecond = articleResponseModel.items.first().updatedMillisecond,
            itemPublishedMillisecond = articleResponseModel.items.first().publishedMillisecond,
            author = articleResponseModel.items.first().author,
            content = articleResponseModel.items.first().summary.content,
            feedId = articleResponseModel.id
        )
        appDatabase.articleDao().insert(articleEntity)
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