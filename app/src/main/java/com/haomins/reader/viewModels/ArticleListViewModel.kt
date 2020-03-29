package com.haomins.reader.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.reader.data.entities.ArticleEntity
import com.haomins.reader.repositories.ArticleListRepository
import com.haomins.reader.utils.DateUtils
import com.haomins.reader.view.fragments.ArticleListFragment
import io.reactivex.observers.DisposableObserver
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

    fun loadArticles(feedId: String) {
        isLoading.postValue(true)
        articleListRepository.loadArticleItemRefs(feedId)
            .subscribe(object : DisposableObserver<List<ArticleEntity>>() {
                override fun onComplete() { Log.d(TAG, "onComplete: called") }
                override fun onNext(t: List<ArticleEntity>) {
                    Log.d(TAG, "onNext: articles loaded -> size: ${t.size}")
                    if (t.isNotEmpty()) {
                        val articleTitleUiItems = t.map {
                            ArticleListFragment.ArticleTitleListUiItem(
                                title = it.itemTitle,
                                postTime = dateUtils.howLongAgo(it.itemPublishedMillisecond),
                                _postTimeMillisecond = it.itemPublishedMillisecond,
                                _itemId = it.itemId
                            )
                        }
                        articleTitleUiItemsList.postValue(articleTitleUiItems.toList())
                        isLoading.postValue(false)
                    }
                }
                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError: ${e.printStackTrace()}")
                }
            })
    }

    fun continueLoadArticles(feedId: String) {
        isLoading.postValue(true)
        articleListRepository.continueLoadArticleItemRefs(feedId)
    }
}