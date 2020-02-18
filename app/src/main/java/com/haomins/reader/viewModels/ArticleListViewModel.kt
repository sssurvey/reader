package com.haomins.reader.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.reader.TheOldReaderService
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

    val articleTitleUiItemsList by lazy {
        MutableLiveData<List<ArticleListFragment.ArticleTitleListUiItem>>()
    }
    val isLoading by lazy {
        MutableLiveData<Boolean>(false)
    }

    private val articleTitleUiItems: MutableSet<ArticleListFragment.ArticleTitleListUiItem> =
        HashSet()
    private var queryResultList: List<ArticleEntity> = ArrayList()

    fun loadArticles(feedId: String) {
        isLoading.postValue(true)
        articleListRepository.loadArticleItemRefs(feedId).subscribe(object : DisposableObserver<List<ArticleEntity>>() {

            override fun onComplete() {
                Log.d(
                    "::DisposableObserver", "onComplete: " +
                            "Query Complete load ${TheOldReaderService.DEFAULT_ARTICLE_AMOUNT} articles"
                )
            }

            override fun onNext(t: List<ArticleEntity>) {
                if (t.isNotEmpty()) {
                    queryResultList = t
                    t.forEach {
                        articleTitleUiItems.add(
                            ArticleListFragment.ArticleTitleListUiItem(
                                title = it.itemTitle,
                                postTime = dateUtils.to24HrString(it.itemPublishedMillisecond),
                                _postTimeMillisecond = it.itemPublishedMillisecond,
                                _itemId = it.itemId
                            )
                        )
                    }
                    articleTitleUiItemsList.postValue(articleTitleUiItems.toList())
                    isLoading.postValue(false)
                }
            }

            override fun onError(e: Throwable) {
                Log.d("::DisposableObserver", "onError: ${e.printStackTrace()}")
            }
        })
    }

    fun continueLoadArticles(feedId: String) {
        isLoading.postValue(true)
        articleListRepository.continueLoadArticleItemRefs(feedId)
    }
}