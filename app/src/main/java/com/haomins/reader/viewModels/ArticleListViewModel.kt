package com.haomins.reader.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class ArticleListViewModel @Inject constructor() : ViewModel() {

    fun sayHi() {
        Log.d("xxx", "hi")
    }

}