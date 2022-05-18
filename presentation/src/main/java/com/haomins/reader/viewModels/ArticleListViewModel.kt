package com.haomins.reader.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.domain.model.entities.ArticleEntity
import com.haomins.domain.usecase.article.ContinueLoadAllArticles
import com.haomins.domain.usecase.article.ContinueLoadArticlesByFeed
import com.haomins.domain.usecase.article.LoadAllArticles
import com.haomins.domain.usecase.article.LoadArticlesByFeed
import io.reactivex.observers.DisposableObserver
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class ArticleListViewModel @Inject constructor(
    private val loadArticlesByFeed: LoadArticlesByFeed,
    private val continueLoadArticlesByFeed: ContinueLoadArticlesByFeed,
    private val continueLoadAllArticles: ContinueLoadAllArticles,
    private val loadAllArticles: LoadAllArticles,
) : ViewModel() {

    companion object {
        const val TAG = "ArticleListViewModel"
    }

    val articleTitleUiItemsListLiveData by lazy { MutableLiveData(articleTitleUiItemsList) }
    val isLoading by lazy { MutableLiveData(false) }
    private val articleTitleUiItemsList = mutableListOf<ArticleEntity>()

    fun loadArticles(feedId: String) {
        isLoading.postValue(true)
        loadArticlesByFeed.execute(
            observer = object : DisposableObserver<List<ArticleEntity>>() {
                override fun onNext(t: List<ArticleEntity>) {
                    onArticleLoaded(t)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: ${e.printStackTrace()}")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: called")
                }
            },
            params = LoadArticlesByFeed.forLoadArticlesByFeed(feedId)
        )
    }

    fun loadAllArticles() {
        isLoading.postValue(true)
        loadAllArticles.execute(
            observer = object : DisposableSingleObserver<List<ArticleEntity>>() {
                override fun onSuccess(t: List<ArticleEntity>) {
                    onArticleLoaded(t)
                    isLoading.postValue(false)
                    Log.d(TAG, "onSuccess: called")
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: ${e.printStackTrace()}")
                }
            }
        )
    }

    fun continueLoadAllArticles() {
        continueLoadAllArticles.execute(
            observer = object : DisposableSingleObserver<List<ArticleEntity>>() {

                override fun onStart() {
                    super.onStart()
                    isLoading.postValue(true)
                }

                override fun onSuccess(t: List<ArticleEntity>) {
                    onArticleLoaded(t)
                    isLoading.postValue(false)
                    Log.d(TAG, "onSuccess: called")
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: ${e.printStackTrace()}")
                }
            }
        )
    }

    fun continueLoadArticles(feedId: String) {
        continueLoadArticlesByFeed.execute(
            observer = object : DisposableObserver<Unit>() {
                override fun onStart() {
                    super.onStart()
                    isLoading.postValue(true)
                }

                override fun onNext(t: Unit) {
                    isLoading.postValue(false)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: ${e.printStackTrace()}")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete: called")
                }
            },
            params = ContinueLoadArticlesByFeed.forContinueLoadArticlesByFeed(feedId)
        )
    }

    override fun onCleared() {
        super.onCleared()
        loadArticlesByFeed.dispose()
        continueLoadArticlesByFeed.dispose()
        loadAllArticles.dispose()
        continueLoadAllArticles.dispose()
    }

    private fun onArticleLoaded(newlyLoadedArticles: List<ArticleEntity>) {
        Log.d(TAG, "onNext: articles loaded -> size: ${newlyLoadedArticles.size}")
        articleTitleUiItemsList.addAll(newlyLoadedArticles.sortedBy { it.itemPublishedMillisecond })
        articleTitleUiItemsListLiveData.postValue(articleTitleUiItemsList)
    }

}