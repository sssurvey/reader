package com.haomins.reader.models.article

import com.google.gson.annotations.SerializedName

data class ArticleResponseModel(

    @SerializedName("direction")
    val direction: String,

    @SerializedName("id")
    val id: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("updated")
    val updated: Long,

    @SerializedName("items")
    val items: List<ArticleItemModel>,

    @SerializedName("author")
    val author: String,

    @SerializedName("likingUsersCount")
    val likingUsersCount: Long

    /**
     * @SerializedName("annotations")
     * @SerializedName("likingUsers")
     * @SerializedName("comments")
     */

)

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
    val title: String,

    @SerializedName("summary")
    val summary: Summary

    /**
     * @SerializedName("published")
     * @SerializedName("updated")
     * @SerializedName("tor_identifier")
     */
)

data class Summary(

    @SerializedName("direction")
    val direction: String,

    @SerializedName("content")
    val content: String

)