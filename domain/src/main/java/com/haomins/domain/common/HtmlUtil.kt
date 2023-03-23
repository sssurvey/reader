package com.haomins.domain.common

import org.jsoup.Jsoup
import javax.inject.Inject

class HtmlUtil @Inject constructor() {

    fun extractImageFromImgTags(rawHtmlString: String): String {
        val document = Jsoup.parse(rawHtmlString)
        val img = document.select("img").first()
        val imgUrl = img?.absUrl("src")
        return imgUrl
            ?: ""
    }

}
