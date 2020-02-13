package com.haomins.reader.models.subscription

import com.google.gson.annotations.SerializedName

data class SubscriptionItemModel(

    @SerializedName("id")
    var id: String = "",

    @SerializedName("title")
    var title: String = "",

    @SerializedName("categories")
    var categories: Array<String> = emptyArray(),

    @SerializedName("sortid")
    var sortId: String = "",

    @SerializedName("firstitemmsec")
    var firstItemMilSec: String = "",

    @SerializedName("url")
    var url: String = "",

    @SerializedName("htmlUrl")
    var htmlUrl: String = "",

    @SerializedName("iconUrl")
    var iconUrl: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SubscriptionItemModel

        if (id != other.id) return false
        if (title != other.title) return false
        if (!categories.contentEquals(other.categories)) return false
        if (sortId != other.sortId) return false
        if (firstItemMilSec != other.firstItemMilSec) return false
        if (url != other.url) return false
        if (htmlUrl != other.htmlUrl) return false
        if (iconUrl != other.iconUrl) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + categories.contentHashCode()
        result = 31 * result + sortId.hashCode()
        result = 31 * result + firstItemMilSec.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + htmlUrl.hashCode()
        result = 31 * result + iconUrl.hashCode()
        return result
    }
}