package com.haomins.www.model.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article_entity")
data class ArticleEntity(

    @PrimaryKey
    @ColumnInfo(name = "item_id")
    val itemId: String,

    @ColumnInfo(name = "feed_id")
    val feedId: String,

    @ColumnInfo(name = "title")
    val itemTitle: String,

    @ColumnInfo(name = "updated")
    val itemUpdatedMillisecond: Long,

    @ColumnInfo(name = "published")
    val itemPublishedMillisecond: Long,

    @ColumnInfo(name = "author")
    val author: String,

    @ColumnInfo(name = "content")
    val content: String

)