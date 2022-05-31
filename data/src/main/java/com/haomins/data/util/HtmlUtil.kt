package com.haomins.data.util

import org.jsoup.Jsoup

fun extractImageFromImgTags(rawHtmlString: String): String {
    val document = Jsoup.parse(rawHtmlString)
    val img = document.select("img").first()
    val imgUrl = img?.absUrl("src")
    return imgUrl
        ?: ""
}