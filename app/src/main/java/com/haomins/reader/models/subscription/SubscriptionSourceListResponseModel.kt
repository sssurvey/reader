package com.haomins.reader.models.subscription

import com.google.gson.annotations.SerializedName

data class SubscriptionSourceListResponseModel(
    @SerializedName("subscriptions")
    val subscriptions: ArrayList<SubscriptionItemModel>
)