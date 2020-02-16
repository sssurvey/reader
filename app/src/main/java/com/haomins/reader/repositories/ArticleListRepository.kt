package com.haomins.reader.repositories

import android.content.SharedPreferences
import android.util.Log
import com.haomins.reader.TheOldReaderService
import com.haomins.reader.data.AppDatabase
import com.haomins.reader.models.article.ArticleResponseModel
import com.haomins.reader.models.article.ItemRefListResponseModel
import com.haomins.reader.utils.getValue
import com.haomins.reader.viewModels.LoginViewModel
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class ArticleListRepository @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val appDatabase: AppDatabase,
    private val sharedPreferences: SharedPreferences
) {

    fun loadArticleItemRefs(feedId: String) {
        theOldReaderService.loadArticleListByFeed(
            headerAuthValue = (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                    + sharedPreferences.getValue(LoginViewModel.AUTH_CODE_KEY)),
            feedId = feedId
        ).subscribe(object : DisposableSingleObserver<ItemRefListResponseModel>() {
            override fun onSuccess(t: ItemRefListResponseModel) {
                loadIndividualArticleInformation(t)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }
        })
    }

    fun loadIndividualArticleInformation(itemRefListResponseModel: ItemRefListResponseModel) {
        itemRefListResponseModel.itemRefs.forEach {
            theOldReaderService.loadArticleDetailsByRefId(
                headerAuthValue = (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                        + sharedPreferences.getValue(LoginViewModel.AUTH_CODE_KEY)),
                refItemId = it.id
            ).subscribe(ArticleResponseModelObserver())
        }
    }

    private fun saveIndividualArticleToDatabase(articleResponseModel: ArticleResponseModel) {
        //TODO: FINISH ArticleResponseModel, save them to DB here
    }

    inner class ArticleResponseModelObserver : DisposableSingleObserver<ArticleResponseModel>() {
        override fun onSuccess(t: ArticleResponseModel) {

            saveIndividualArticleToDatabase(t)

            Log.d("xxxxx yyyy", "${t.items[0].summary}")
            Log.d("xxxxx", "ArticleResponseMode: ---> ${t.direction}")
            Log.d("xxxxx", "ArticleResponseMode: ---> ${t.id}")
            Log.d("xxxxx", "ArticleResponseMode: ---> ${t.title}")
            Log.d("xxxxx", "ArticleResponseMode: ---> ${t.description}")
        }

        override fun onError(e: Throwable) {
            e.printStackTrace()
        }
    }

}