package com.haomins.reader.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.reader.utils.DateUtils
import com.haomins.reader.view.fragments.ArticleListFragment
import com.haomins.www.model.model.entities.ArticleEntity
import com.haomins.www.model.repositories.ArticleListRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ArticleListViewModel @Inject constructor(
        private val articleListRepository: ArticleListRepository,
        private val dateUtils: DateUtils
) : ViewModel() {

    companion object {
        const val TAG = "ArticleListViewModel"
    }

    val articleTitleUiItemsList by lazy { MutableLiveData<List<ArticleListFragment.ArticleTitleListUiItem>>() }
    val isLoading by lazy { MutableLiveData(false) }
    private val disposables = CompositeDisposable()

    fun loadArticles(feedId: String) {
        isLoading.postValue(true)
        disposables.add(
                articleListRepository
                        .loadArticleItemRefs(feedId)
                        .map(::mapEntitiesToUiItems)
                        .subscribe(
                                { onArticleLoaded(it) },
                                { Log.d(TAG, "onError: ${it.printStackTrace()}") },
                                { Log.d(TAG, "onComplete: called") })
        )
    }

    fun loadAllArticles() {
        isLoading.postValue(true)
        disposables.add(
                articleListRepository
                        .loadAllArticleItemRefs()
                        .map(::mapEntitiesToUiItems)
                        .subscribe(
                                { onArticleLoaded(it) },
                                { Log.d(TAG, "onError: ${it.printStackTrace()}") },
                                { Log.d(TAG, "onComplete: called") })
        )
    }

    fun continueLoadAllArticles() {
        disposables.add(
                articleListRepository
                        .continueLoadAllArticleItemRefs()
                        .doOnSubscribe { isLoading.postValue(true) }
                        .subscribe(
                                { isLoading.postValue(false) },
                                { Log.d(TAG, "onError: ${it.printStackTrace()}") }
                        )
        )
    }

    fun continueLoadArticles(feedId: String) {
        disposables.add(
                articleListRepository
                        .continueLoadArticleItemRefs(feedId)
                        .doOnSubscribe { isLoading.postValue(true) }
                        .subscribe(
                                { isLoading.postValue(false) },
                                { Log.d(TAG, "onError: ${it.printStackTrace()}") }
                        )
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    private fun onArticleLoaded(articleTitleListUiItems: List<ArticleListFragment.ArticleTitleListUiItem>) {
        Log.d(TAG, "onNext: articles loaded -> size: ${articleTitleListUiItems.size}")
        articleTitleUiItemsList.postValue(articleTitleListUiItems.toList())
        isLoading.postValue(false)
    }

    private fun mapEntitiesToUiItems(articleEntities: List<ArticleEntity>): List<ArticleListFragment.ArticleTitleListUiItem> {
        return articleEntities.map {
            ArticleListFragment.ArticleTitleListUiItem(
                    title = it.itemTitle,
                    postTime = dateUtils.howLongAgo(it.itemPublishedMillisecond),
                    _postTimeMillisecond = it.itemPublishedMillisecond,
                    _itemId = it.itemId
            )
        }
    }
}