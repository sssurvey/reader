package com.haomins.www.data.model.responses.subscription

import com.google.gson.annotations.SerializedName

data class AddSourceResponseModel(

        @SerializedName("query")
        val query: String,

        @SerializedName("numResults")
        val numResults: Int,

        @SerializedName("streamId")
        val streamId: String?,

        @SerializedName("error")
        val error: String?
)