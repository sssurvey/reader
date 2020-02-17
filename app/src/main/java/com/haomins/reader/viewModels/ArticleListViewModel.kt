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

    private val articleQueryObserver by lazy {
        object : DisposableObserver<List<ArticleEntity>>() {

            override fun onComplete() {
                Log.d("::DisposableObserver", "onComplete: " +
                        "Query Complete load ${TheOldReaderService.DEFAULT_ARTICLE_AMOUNT} articles")
                dispose()
            }

            override fun onNext(t: List<ArticleEntity>) {
                if (t.size == TheOldReaderService.DEFAULT_ARTICLE_AMOUNT) {
                    val articleTitleUiItems: MutableList<ArticleListFragment.ArticleTitleListUiItem> = ArrayList()
                    t.forEach {
                        articleTitleUiItems.add(
                            ArticleListFragment.ArticleTitleListUiItem(
                                title = it.itemTitle,
                                postTime = dateUtils.to24HrString(it.itemPublishedMillisecond.toLong())
                            )
                        )
                    }
                    articleTitleUiItemsList.postValue(articleTitleUiItems)
                    onComplete()
                }
            }

            override fun onError(e: Throwable) {
                Log.d("::DisposableObserver", "onError: ${e.printStackTrace()}")
            }
        }
    }

    fun loadArticles(feedId: String) {
        articleListRepository.loadArticleItemRefs(feedId).subscribe(articleQueryObserver)
    }

    fun disposeObservers() {
        if (!articleQueryObserver.isDisposed) {
            articleQueryObserver.dispose()
        }
    }

}