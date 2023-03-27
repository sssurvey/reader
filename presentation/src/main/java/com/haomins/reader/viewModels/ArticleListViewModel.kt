package com.haomins.reader.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.haomins.domain.usecase.article.LoadAllArticlesPaged
import com.haomins.model.entity.ArticleEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

@HiltViewModel
class ArticleListViewModel @Inject constructor(
    private val loadAllArticlesPaged: LoadAllArticlesPaged,
) : ViewModel() {

    companion object {
        const val TAG = "ArticleListViewModel"
    }

    //TODO: 143 double check later
    fun loadAllArticles(onArticleUpdated: (PagingData<ArticleEntity>) -> Unit) {
        loadAllArticlesPaged.execute(
            object : DisposableSubscriber<PagingData<ArticleEntity>>() {
                override fun onNext(t: PagingData<ArticleEntity>?) {
                    t?.let { onArticleUpdated.invoke(it) }
                }

                override fun onError(t: Throwable?) {
                    Log.e(TAG, "onError: ${t?.message}")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete")
                }
            }
        )
    }

    fun loadAllArticlesFromFeed(
        feedId: String,
        onArticleUpdated: (PagingData<ArticleEntity>) -> Unit
    ) {
        loadAllArticlesPaged.execute(
            object : DisposableSubscriber<PagingData<ArticleEntity>>() {
                override fun onNext(t: PagingData<ArticleEntity>?) {
                    t?.let { onArticleUpdated.invoke(it) }
                }

                override fun onError(t: Throwable?) {
                    Log.e(TAG, "onError: ${t?.message}")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete")
                }
            },
            params = LoadAllArticlesPaged.forLoadAllArticlesPaged(feedId)
        )
    }

    override fun onCleared() {
        super.onCleared()
        loadAllArticlesPaged.dispose()
    }

}