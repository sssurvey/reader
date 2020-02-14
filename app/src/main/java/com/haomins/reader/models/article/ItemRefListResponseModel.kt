package com.haomins.reader.models.article

import com.google.gson.annotations.SerializedName

data class ItemRefListResponseModel(

    @SerializedName("itemRefs")
    val itemRefs: List<RefItemModel>

)

data class RefItemModel(

    @SerializedName("id")
    val id: String,

    @SerializedName("directStreamIds")
    val directStreamIds: List<String>, //<--- debatable

    @SerializedName("timestampUsec")
    val timestampUsec: String

)