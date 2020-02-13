package com.haomins.reader.models.article

import com.google.gson.annotations.SerializedName

data class ItemRefListResponseModel(
    @SerializedName("itemRefs")
    val itemRefs: List<RefItemModel>
)