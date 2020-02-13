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
    val description: String
    //TODO: complete this

)