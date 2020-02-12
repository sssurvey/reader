package com.haomins.reader.models.subscription

import com.google.gson.annotations.SerializedName

data class SubscriptionItemModel(

    @SerializedName("id")
    val id: String = "",

    @SerializedName("title")
    val title: String = "",

    @SerializedName("categories")
    val categories: Array<String> = emptyArray(),

    @SerializedName("sortid")
    val sortId: String = "",

    @SerializedName("firstitemmsec")
    val firstItemMilSec: String = "",

    @SerializedName("url")
    val url: String = "",

    @SerializedName("htmlUrl")
    val htmlUrl: String = "",

    @SerializedName("iconUrl")
    val iconUrl: String = ""
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