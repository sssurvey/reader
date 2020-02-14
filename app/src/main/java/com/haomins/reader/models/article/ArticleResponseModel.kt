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
    val items: List<ArticleItemResponseModel>,

    @SerializedName("author")
    val author: String,

    @SerializedName("likingUsersCount")
    val likingUsersCount: Long

    /**
     * TODO: complete this later
     * @SerializedName("annotations")
     * @SerializedName("likingUsers")
     * @SerializedName("comments")
     */

)