package com.haomins.reader.models.subscription

import com.google.gson.annotations.SerializedName

data class SubscriptionSourceListResponse(
    @SerializedName("subscriptions")
    val subscriptions: ArrayList<SubscriptionItem>
)