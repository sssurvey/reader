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
import javax.inject.Inject

class ArticleListViewModel @Inject constructor(
    private val loadAllArticles: LoadAllArticles,
    private val loadArticlesByFeed: LoadArticlesByFeed,
    private val continueLoadAllArticles: ContinueLoadAllArticles,
    private val continueLoadArticlesByFeed: ContinueLoadArticlesByFeed
) : ViewModel() {

    companion object {
        const val TAG = "ArticleListViewModel"
    }

    val articleTitleUiItemsList by lazy { MutableLiveData<List<ArticleEntity>>() }
    val isLoading by lazy { MutableLiveData(false) }

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
            }
        )
    }

    fun continueLoadAllArticles() {
        continueLoadAllArticles.execute(
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
        loadAllArticles.dispose()
        continueLoadAllArticles.dispose()
        continueLoadArticlesByFeed.dispose()
    }

    private fun onArticleLoaded(articleTitleListUiItems: List<ArticleEntity>) {
        Log.d(TAG, "onNext: articles loaded -> size: ${articleTitleListUiItems.size}")
        articleTitleUiItemsList.postValue(articleTitleListUiItems.toList())
        isLoading.postValue(false)
    }

}