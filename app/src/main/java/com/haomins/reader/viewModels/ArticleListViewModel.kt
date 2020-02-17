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

    private val articleTitleUiItems: MutableSet<ArticleListFragment.ArticleTitleListUiItem> =
        HashSet()

    fun loadArticles(feedId: String) {
        articleListRepository.loadArticleItemRefs(feedId).subscribe(object : DisposableObserver<List<ArticleEntity>>() {

            override fun onComplete() {
                Log.d(
                    "::DisposableObserver", "onComplete: " +
                            "Query Complete load ${TheOldReaderService.DEFAULT_ARTICLE_AMOUNT} articles"
                )
            }

            override fun onNext(t: List<ArticleEntity>) {
                if (t.isNotEmpty()) {
                    t.forEach {
                        articleTitleUiItems.add(
                            ArticleListFragment.ArticleTitleListUiItem(
                                title = it.itemTitle,
                                postTime = dateUtils.to24HrString(it.itemPublishedMillisecond)
                            )
                        )
                    }
                    articleTitleUiItemsList.postValue(articleTitleUiItems.toList())
                }
            }

            override fun onError(e: Throwable) {
                Log.d("::DisposableObserver", "onError: ${e.printStackTrace()}")
            }
        })
    }

    fun continueLoadArticles(feedId: String) {
        articleListRepository.continueLoadArticleItemRefs(feedId)
    }

}