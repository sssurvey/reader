package com.haomins.reader.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.domain.usecase.article.ContinueLoadAllArticlesAndSaveToLocal
import com.haomins.domain.usecase.article.ContinueLoadArticlesByFeed
import com.haomins.domain.usecase.article.LoadAllArticlesAndSaveToLocal
import com.haomins.domain.usecase.article.LoadArticlesByFeed
import com.haomins.model.entity.ArticleEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val loadArticlesByFeed: LoadArticlesByFeed,
    private val continueLoadArticlesByFeed: ContinueLoadArticlesByFeed,
    private val loadAllArticlesAndSaveToLocal: LoadAllArticlesAndSaveToLocal,
    private val continueLoadAllArticlesAndSaveToLocal: ContinueLoadAllArticlesAndSaveToLocal
) : ViewModel() {

    companion object {
        const val TAG = "ArticleListViewModel"
    }

    private val articleTitleUiItems = mutableSetOf<ArticleEntity>()
    private val _articleTitleUiItemsLiveData =
        MutableLiveData<Set<ArticleEntity>>(articleTitleUiItems)
    val articleTitleUiItemsListLiveData: LiveData<Set<ArticleEntity>> =
        _articleTitleUiItemsLiveData
    val isLoading by lazy { MutableLiveData(false) }

    fun loadArticles(feedId: String) {
        isLoading.postValue(true)
        Log.d(TAG, "loadArticles called")
        loadArticlesByFeed.execute(
            observer = object : DisposableSingleObserver<List<ArticleEntity>>() {
                override fun onSuccess(t: List<ArticleEntity>) {
                    onArticleLoaded(t)
                    Log.d(TAG, "loadArticlesByFeed :: onSuccess: called")
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError: ${e.printStackTrace()}")
                }
            },
            params = LoadArticlesByFeed.forLoadArticlesByFeed(feedId)
        )
    }

    fun loadAllArticles() {
        isLoading.postValue(true)
        Log.d(TAG, "loadAllArticles called")
        loadAllArticlesAndSaveToLocal.execute(
            observer = object : DisposableSingleObserver<List<ArticleEntity>>() {
                override fun onSuccess(t: List<ArticleEntity>) {
                    onArticleLoaded(t)
                    isLoading.postValue(false)
                    Log.d(TAG, "loadAllArticlesAndSaveToLocal :: onSuccess: called")
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "loadAllArticlesAndSaveToLocal :: onError: ${e.printStackTrace()}")
                }
            }
        )
    }

    fun continueLoadAllArticles() {
        Log.d(TAG, "continueLoadAllArticles called")
        continueLoadAllArticlesAndSaveToLocal.execute(
            observer = object : DisposableSingleObserver<List<ArticleEntity>>() {
                override fun onStart() {
                    super.onStart()
                    isLoading.postValue(true)
                }

                override fun onSuccess(t: List<ArticleEntity>) {
                    onArticleLoaded(t)
                    isLoading.postValue(false)
                    Log.d(TAG, "continueLoadAllArticlesAndSaveToLocal :: onSuccess: called")
                }

                override fun onError(e: Throwable) {
                    Log.e(
                        TAG,
                        "continueLoadAllArticlesAndSaveToLocal :: onError: ${e.printStackTrace()}"
                    )
                }
            }
        )
    }

    fun continueLoadArticles(feedId: String) {
        Log.d(TAG, "continueLoadArticles called")
        continueLoadArticlesByFeed.execute(
            observer = object : DisposableSingleObserver<List<ArticleEntity>>() {
                override fun onStart() {
                    super.onStart()
                    isLoading.postValue(true)
                }

                override fun onSuccess(t: List<ArticleEntity>) {
                    onArticleLoaded(t)
                    isLoading.postValue(false)
                    Log.d(TAG, "continueLoadArticles :: onSuccess: called")
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "continueLoadArticles :: onError: ${e.printStackTrace()}")
                }
            },
            params = ContinueLoadArticlesByFeed.forContinueLoadArticlesByFeed(feedId)
        )
    }

    override fun onCleared() {
        super.onCleared()
        loadArticlesByFeed.dispose()
        continueLoadArticlesByFeed.dispose()
        loadAllArticlesAndSaveToLocal.dispose()
        continueLoadAllArticlesAndSaveToLocal.dispose()
    }

    private fun onArticleLoaded(newlyLoadedArticles: List<ArticleEntity>) {
        Log.d(TAG, "onArticleLoaded: articles loaded -> size: ${newlyLoadedArticles.size}")
        articleTitleUiItems.addAll(newlyLoadedArticles)
        _articleTitleUiItemsLiveData.postValue(
            articleTitleUiItems.sortedByDescending {
                it.itemPublishedMillisecond
            }.toSet()
        )
    }

}