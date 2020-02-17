package com.haomins.reader.repositories

import android.content.SharedPreferences
import com.haomins.reader.TheOldReaderService
import com.haomins.reader.data.AppDatabase
import com.haomins.reader.data.entities.ArticleEntity
import com.haomins.reader.models.article.ArticleResponseModel
import com.haomins.reader.models.article.ItemRefListResponseModel
import com.haomins.reader.utils.getValue
import com.haomins.reader.viewModels.LoginViewModel
import io.reactivex.Observable
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class ArticleListRepository @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val appDatabase: AppDatabase,
    private val sharedPreferences: SharedPreferences
) {

    fun loadArticleItemRefs(feedId: String): Observable<List<ArticleEntity>> {
        theOldReaderService.loadArticleListByFeed(
            headerAuthValue = (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                    + sharedPreferences.getValue(LoginViewModel.AUTH_CODE_KEY)),
            feedId = feedId
        ).subscribe(object : DisposableSingleObserver<ItemRefListResponseModel>() {
            override fun onSuccess(t: ItemRefListResponseModel) {
                if (t.itemRefs.isNotEmpty()) loadIndividualArticleInformation(t)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }
        })

        return appDatabase.articleDao().selectAllArticleByFeedId(feedId)
    }

    private fun loadIndividualArticleInformation(itemRefListResponseModel: ItemRefListResponseModel) {
        itemRefListResponseModel.itemRefs.forEach {
            theOldReaderService.loadArticleDetailsByRefId(
                headerAuthValue = (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                        + sharedPreferences.getValue(LoginViewModel.AUTH_CODE_KEY)),
                refItemId = it.id
            ).subscribe(ArticleResponseModelObserver())
        }
    }

    private fun saveIndividualArticleToDatabase(articleResponseModel: ArticleResponseModel) {
        val articleEntity = ArticleEntity(
            itemId = articleResponseModel.items.first().id,
            itemTitle = articleResponseModel.items.first().title,
            itemUpdatedMillisecond = articleResponseModel.items.first().updatedMillisecond.toString(),
            itemPublishedMillisecond = articleResponseModel.items.first().publishedMillisecond.toString(),
            author = articleResponseModel.items.first().author,
            content = articleResponseModel.items.first().summary.content,
            feedId = articleResponseModel.id
        )
        appDatabase.articleDao().insert(articleEntity)
    }

    private inner class ArticleResponseModelObserver :
        DisposableSingleObserver<ArticleResponseModel>() {
        override fun onSuccess(t: ArticleResponseModel) {
            saveIndividualArticleToDatabase(t)
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
        }
    }

}