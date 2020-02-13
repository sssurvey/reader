package com.haomins.reader.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.haomins.reader.repositories.ArticleListRepository
import javax.inject.Inject

class ArticleListViewModel @Inject constructor(
    private val articleListRepository: ArticleListRepository
) : ViewModel() {

    fun sayHi() {
        Log.d("xxx", "hi")
    }

}