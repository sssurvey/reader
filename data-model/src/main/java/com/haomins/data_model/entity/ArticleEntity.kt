package com.haomins.data_model.entity

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
    val content: String,

    @ColumnInfo(name = "href")
    val href: String,

    @ColumnInfo(name = "preview_image_url")
    val previewImageUrl: String
)