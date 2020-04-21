package com.haomins.www.core.data.models.subscription

import com.google.gson.annotations.SerializedName

data class AddSubscriptionResponseModel(

    @SerializedName("query")
    val query: String,

    @SerializedName("numResults")
    val numResults: Int,

    @SerializedName("streamId")
    val streamId: String
)