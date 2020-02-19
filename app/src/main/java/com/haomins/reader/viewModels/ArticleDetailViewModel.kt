package com.haomins.reader.viewModels

import androidx.lifecycle.ViewModel
import com.haomins.reader.data.entities.ArticleEntity
import com.haomins.reader.repositories.ArticleDetailRepository
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class ArticleDetailViewModel @Inject constructor(
    private val articleDetailRepository: ArticleDetailRepository
): ViewModel() {

    fun loadArticleDetail(itemId: String) {
        articleDetailRepository.loadArticleDetail(itemId).subscribe(
            object : DisposableSingleObserver<ArticleEntity>() {
                override fun onSuccess(t: ArticleEntity) {

                }

                override fun onError(e: Throwable) {

                }
            }
        )
    }

}