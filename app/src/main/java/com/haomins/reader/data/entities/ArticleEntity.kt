package com.haomins.reader.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article_entity")
data class ArticleEntity(

    @PrimaryKey
    @ColumnInfo(name = "item_id")
    val articleId: String,

    @ColumnInfo(name = "feed_id")
    val feedId: String,

    @ColumnInfo(name = "title")
    val articleTitle: String,

    @ColumnInfo(name = "updated")
    val articleUpdatedMillisecond: String,

    @ColumnInfo(name = "published")
    val articlePublishedMillisecond: String,

    @ColumnInfo(name = "author")
    val author: String,

    @ColumnInfo(name = "content")
    val content: String

)