package com.haomins.reader.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.domain.model.entities.ArticleEntity
import com.haomins.domain.usecase.article.ContinueLoadAllArticles
import com.haomins.domain.usecase.article.ContinueLoadArticlesByFeed
import com.haomins.domain.usecase.article.LoadAllArticles
import com.haomins.domain.usecase.article.LoadArticlesByFeed
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val loadArticlesByFeed: LoadArticlesByFeed,
    private val continueLoadArticlesByFeed: ContinueLoadArticlesByFeed,
    private val continueLoadAllArticles: ContinueLoadAllArticles,
    private val loadAllArticles: LoadAllArticles,
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
        loadAllArticles.execute(
            observer = object : DisposableSingleObserver<List<ArticleEntity>>() {
                override fun onSuccess(t: List<ArticleEntity>) {
                    onArticleLoaded(t)
                    isLoading.postValue(false)
                    Log.d(TAG, "loadAllArticles :: onSuccess: called")
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "loadAllArticles :: onError: ${e.printStackTrace()}")
                }
            }
        )
    }

    fun continueLoadAllArticles() {
        Log.d(TAG, "continueLoadAllArticles called")
        continueLoadAllArticles.execute(
            observer = object : DisposableSingleObserver<List<ArticleEntity>>() {

                override fun onStart() {
                    super.onStart()
                    isLoading.postValue(true)
                }

                override fun onSuccess(t: List<ArticleEntity>) {
                    onArticleLoaded(t)
                    isLoading.postValue(false)
                    Log.d(TAG, "continueLoadAllArticles :: onSuccess: called")
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "continueLoadAllArticles :: onError: ${e.printStackTrace()}")
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
        loadAllArticles.dispose()
        continueLoadAllArticles.dispose()
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