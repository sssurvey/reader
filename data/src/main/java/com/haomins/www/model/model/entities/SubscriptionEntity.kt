package com.haomins.www.model.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "subscription_entity")
data class SubscriptionEntity(

        @PrimaryKey
        @ColumnInfo(name = "id")
        var id: String = "",

        @ColumnInfo(name = "title")
        var title: String = "",

        @ColumnInfo(name = "sortid")
        var sortId: String = "",

        @ColumnInfo(name = "firstitemmsec")
        var firstItemMilSec: String = "",

        @ColumnInfo(name = "url")
        var url: String = "",

        @ColumnInfo(name = "htmlUrl")
        var htmlUrl: String = "",

        @ColumnInfo(name = "iconUrl")
        var iconUrl: String = "",

        @Ignore // <--- Add support later on
        var categories: Array<String> = emptyArray()

) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SubscriptionEntity

        if (id != other.id) return false
        if (title != other.title) return false
        if (sortId != other.sortId) return false
        if (firstItemMilSec != other.firstItemMilSec) return false
        if (url != other.url) return false
        if (htmlUrl != other.htmlUrl) return false
        if (iconUrl != other.iconUrl) return false
        if (!categories.contentEquals(other.categories)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + sortId.hashCode()
        result = 31 * result + firstItemMilSec.hashCode()
        result = 31 * result + url.hashCode()
        result = 31 * result + htmlUrl.hashCode()
        result = 31 * result + iconUrl.hashCode()
        result = 31 * result + categories.contentHashCode()
        return result
    }
}