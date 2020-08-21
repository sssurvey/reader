package com.haomins.reader.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.reader.utils.DarkModeManager
import com.haomins.reader.utils.DateUtils
import com.haomins.reader.view.fragments.ArticleDetailFragment
import com.haomins.www.model.data.entities.ArticleEntity
import com.haomins.www.model.repositories.ArticleDetailRepository
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ArticleDetailViewModel @Inject constructor(
    private val articleDetailRepository: ArticleDetailRepository,
    private val dateUtils: DateUtils,
    private val darkModeManager: DarkModeManager
) : ViewModel() {

    companion object {
        const val TAG = "ArticleDetailViewModel"
    }

    private val disposables = CompositeDisposable()

    val contentDataForDisplay by lazy {
        MutableLiveData<ArticleDetailFragment.ArticleDetailUiItem>()
    }

    fun loadArticleDetail(itemId: String) {
        disposables.add(
            articleDetailRepository
                .loadArticleDetail(itemId)
                .flatMap { mapArticleEntityToUiItem(it) }
                .subscribe(
                    { contentDataForDisplay.postValue(it) },
                    { Log.d(TAG, "onError :: ${it.printStackTrace()}") }
                )
        )
    }

    fun isDarkModeEnabled(): Boolean {
        return darkModeManager.checkIsCurrentDarkModeEnabled()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    private fun mapArticleEntityToUiItem(articleEntity: ArticleEntity): Single<ArticleDetailFragment.ArticleDetailUiItem> {
        return Single.fromCallable {
            ArticleDetailFragment.ArticleDetailUiItem(
                title = articleEntity.itemTitle,
                updateTime = dateUtils.to24HrString(articleEntity.itemUpdatedMillisecond),
                author = articleEntity.author,
                contentHtmlData = articleEntity.content
            )
        }
    }

}