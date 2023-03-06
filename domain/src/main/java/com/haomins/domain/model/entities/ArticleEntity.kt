package com.haomins.domain.model.entities

data class ArticleEntity(
    val itemTitle: String,
    val itemId: String,
    val author: String,
    val content: String,
    val howLongAgo: String,
    val updatedTime: String,
    val itemUpdatedMillisecond: Long,
    val itemPublishedMillisecond: Long,
    val href: String,
    val previewImageUrl: String
)