package com.haomins.reader.models.subscription

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class SubscriptionItemModel(

    @PrimaryKey
    @SerializedName("id")
    var id: String = "",

    @ColumnInfo(name = "title")
    @SerializedName("title")
    var title: String = "",

    /*ROOM does not support auto convert of list to DB entities, ignore it for now*/
    @Ignore
    @SerializedName("categories")
    var categories: Array<String> = emptyArray(),

    @ColumnInfo(name = "sortid")
    @SerializedName("sortid")
    var sortId: String = "",

    @ColumnInfo(name = "firstitemmsec")
    @SerializedName("firstitemmsec")
    var firstItemMilSec: String = "",

    @ColumnInfo(name = "url")
    @SerializedName("url")
    var url: String = "",

    @ColumnInfo(name = "htmlUrl")
    @SerializedName("htmlUrl")
    var htmlUrl: String = "",

    @ColumnInfo(name = "iconUrl")
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