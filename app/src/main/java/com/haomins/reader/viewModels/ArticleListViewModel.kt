package com.haomins.reader.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.reader.TheOldReaderService
import com.haomins.reader.data.entities.ArticleEntity
import com.haomins.reader.repositories.ArticleListRepository
import com.haomins.reader.utils.DateUtils
import com.haomins.reader.view.fragments.ArticleListFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject

class ArticleListViewModel @Inject constructor(
    private val articleListRepository: ArticleListRepository,
    private val dateUtils: DateUtils
) : ViewModel() {

    val articleTitleUiItemsList by lazy {
        MutableLiveData<List<ArticleListFragment.ArticleTitleListUiItem>>()
    }

    private val articleTitleUiItems: MutableList<ArticleListFragment.ArticleTitleListUiItem> =
        ArrayList()
    private val compositeDisposable = CompositeDisposable()

    fun loadArticles(feedId: String) {
        articleListRepository.loadArticleItemRefs(feedId).subscribe(ArticleQueryObserver())
    }

    fun continueLoadArticles(feedId: String) {
        articleListRepository.continueLoadArticleItemRefs(feedId).subscribe(ArticleQueryObserver())
    }

    fun disposeObservers() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    inner class ArticleQueryObserver : DisposableObserver<List<ArticleEntity>>() {

        init {
            compositeDisposable.add(this)
        }

        override fun onComplete() {
            Log.d(
                "::DisposableObserver", "onComplete: " +
                        "Query Complete load ${TheOldReaderService.DEFAULT_ARTICLE_AMOUNT} articles"
            )
            dispose()
        }

        override fun onNext(t: List<ArticleEntity>) {
            if (t.size >= TheOldReaderService.DEFAULT_ARTICLE_AMOUNT) {
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