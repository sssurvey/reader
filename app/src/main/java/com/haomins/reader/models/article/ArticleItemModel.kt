package com.haomins.reader.models.article

import com.google.gson.annotations.SerializedName

data class ArticleItemModel(

    @SerializedName("crawlTimeMsec")
    val crawlTimeMsec: String,

    @SerializedName("timestampUsec")
    val timestampUsec: String,

    @SerializedName("id")
    val id: String,

    @SerializedName("categories")
    val categories: List<String>,

    @SerializedName("title")
    val title: String

//    @SerializedName("published")
//    @SerializedName("updated")
//    @SerializedName("tor_identifier")
)

data class Summary(

    @SerializedName("direction")
    val direction: String,

    @SerializedName("content")
    val content: String

)