package com.haomins.www.model.data.models.subscription

import com.google.gson.annotations.SerializedName

data class AddSubscriptionResponseModel(

    @SerializedName("query")
    val query: String,

    @SerializedName("numResults")
    val numResults: Int,

    @SerializedName("streamId")
    val streamId: String?,

    @SerializedName("error")
    val error: String?
)