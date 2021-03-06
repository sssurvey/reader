package com.haomins.reader.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.domain.model.entities.ArticleEntity
import com.haomins.domain.usecase.articledetails.LoadArticleData
import com.haomins.reader.utils.DarkModeManager
import com.haomins.reader.view.fragments.ArticleDetailFragment
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class ArticleDetailViewModel @Inject constructor(
    private val loadArticleData: LoadArticleData,
    private val darkModeManager: DarkModeManager
) : ViewModel() {

    companion object {
        const val TAG = "ArticleDetailViewModel"
    }

    private val _contentDataForDisplay by lazy {
        MutableLiveData<ArticleDetailFragment.ArticleDetailUiItem>()
    }
    val contentDataForDisplay: LiveData<ArticleDetailFragment.ArticleDetailUiItem> = _contentDataForDisplay

    fun loadArticleDetail(itemId: String) {
        loadArticleData.execute(
                object : DisposableSingleObserver<ArticleEntity>() {
                    override fun onSuccess(t: ArticleEntity) {
                        _contentDataForDisplay.postValue(mapArticleEntityToUiItem(t))
                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG, "loadArticleData :: onError${e.printStackTrace()}")
                    }

                },
                params = LoadArticleData.forLoadArticleContent(articleId = itemId)
        )
    }

    fun isDarkModeEnabled(): Boolean {
        return darkModeManager.checkIsCurrentDarkModeEnabled()
    }

    override fun onCleared() {
        super.onCleared()
        loadArticleData.dispose()
    }

    private fun mapArticleEntityToUiItem(articleEntity: ArticleEntity): ArticleDetailFragment.ArticleDetailUiItem {
        return ArticleDetailFragment.ArticleDetailUiItem(
                title = articleEntity.itemTitle,
                updateTime = articleEntity.updatedTime,
                author = articleEntity.author,
                contentHtmlData = articleEntity.content
        )
    }

}