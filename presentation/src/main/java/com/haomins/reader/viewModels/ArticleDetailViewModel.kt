package com.haomins.reader.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.reader.utils.DarkModeManager
import com.haomins.reader.utils.DateUtils
import com.haomins.reader.view.fragments.ArticleDetailFragment
import com.haomins.www.core.data.entities.ArticleEntity
import com.haomins.www.core.repositories.ArticleDetailRepository
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class ArticleDetailViewModel @Inject constructor(
    private val articleDetailRepository: ArticleDetailRepository,
    private val dateUtils: DateUtils,
    private val darkModeManager: DarkModeManager
) : ViewModel() {

    val contentDataForDisplay by lazy {
        MutableLiveData<ArticleDetailFragment.ArticleDetailUiItem>()
    }

    fun loadArticleDetail(itemId: String) {
        articleDetailRepository.loadArticleDetail(itemId).subscribe(
            object : DisposableSingleObserver<ArticleEntity>() {
                override fun onSuccess(t: ArticleEntity) {
                    val articleData = ArticleDetailFragment.ArticleDetailUiItem(
                        title = t.itemTitle,
                        updateTime = dateUtils.to24HrString(t.itemUpdatedMillisecond),
                        author = t.author,
                        contentHtmlData = t.content
                    )
                    contentDataForDisplay.postValue(articleData)
                }

                override fun onError(e: Throwable) {

                }
            }
        )
    }

    fun isDarkModeEnabled(): Boolean {
        return darkModeManager.checkIsCurrentDarkModeEnabled()
    }

}