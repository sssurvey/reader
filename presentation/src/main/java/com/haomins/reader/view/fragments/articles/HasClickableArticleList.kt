package com.haomins.reader.view.fragments.articles

interface HasClickableArticleList {

    fun startArticleDetailActivity(
        articleItemId: String,
        articleItemIdArray: Array<String>
    )

}