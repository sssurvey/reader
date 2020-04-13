package com.haomins.reader.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.reader.utils.DateUtils
import com.haomins.reader.view.fragments.ArticleListFragment
import com.haomins.www.core.data.entities.ArticleEntity
import com.haomins.www.core.repositories.ArticleListRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ArticleListViewModel @Inject constructor(
    private val articleListRepository: ArticleListRepository,
    private val dateUtils: DateUtils
) : ViewModel() {

    companion object {
        const val TAG = "ArticleListViewModel"
    }

    val articleTitleUiItemsList by lazy {
        MutableLiveData<List<ArticleListFragment.ArticleTitleListUiItem>>()
    }
    val isLoading by lazy {
        MutableLiveData(false)
    }

    private val disposables = CompositeDisposable()

    fun loadArticles(feedId: String) {
        isLoading.postValue(true)
        disposables.add(articleListRepository.loadArticleItemRefs(feedId)
            .distinctUntilChanged(List<ArticleEntity>::size)
            .map { list ->
                val articleTitleUiItems = list.map {
                    ArticleListFragment.ArticleTitleListUiItem(
                        title = it.itemTitle,
                        postTime = dateUtils.howLongAgo(it.itemPublishedMillisecond),
                        _postTimeMillisecond = it.itemPublishedMillisecond,
                        _itemId = it.itemId
                    )
                }
                articleTitleUiItems
            }.subscribe({
                Log.d(TAG, "onNext: articles loaded -> size: ${it.size}")
                articleTitleUiItemsList.postValue(it.toList())
                isLoading.postValue(false)
            }, { Log.d(TAG, "onError: ${it.printStackTrace()}") },
                { Log.d(TAG, "onComplete: called") })
        )
    }

    fun loadAllArticles() {
        isLoading.postValue(true)
        disposables.add(articleListRepository.loadAllArticleItemRefs()
            .distinctUntilChanged(List<ArticleEntity>::size)
            .map { list ->
                val articleTitleUiItems = list.map {
                    ArticleListFragment.ArticleTitleListUiItem(
                        title = it.itemTitle,
                        postTime = dateUtils.howLongAgo(it.itemPublishedMillisecond),
                        _postTimeMillisecond = it.itemPublishedMillisecond,
                        _itemId = it.itemId
                    )
                }
                articleTitleUiItems
            }.subscribe({
                Log.d(TAG, "onNext: articles loaded -> size: ${it.size}")
                articleTitleUiItemsList.postValue(it.toList())
                isLoading.postValue(false)
            }, { Log.d(TAG, "onError: ${it.printStackTrace()}") },
                { Log.d(TAG, "onComplete: called") })
        )
    }

    fun continueLoadAllArticles() {
        isLoading.postValue(true)
        articleListRepository.continueLoadAllArticleItemRefs {
            isLoading.postValue(false)
        }
    }

    fun continueLoadArticles(feedId: String) {
        isLoading.postValue(true)
        articleListRepository.continueLoadArticleItemRefs(feedId) {
            isLoading.postValue(false)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}