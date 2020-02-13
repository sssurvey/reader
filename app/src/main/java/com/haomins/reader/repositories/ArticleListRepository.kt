package com.haomins.reader.repositories

import android.content.SharedPreferences
import android.util.Log
import com.haomins.reader.TheOldReaderService
import com.haomins.reader.data.AppDatabase
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
                Log.d("xxx", "${t.itemRefs}")
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }
        })
    }

}