package com.haomins.domain.common

import com.haomins.model.entity.ArticleEntity
import com.haomins.model.remote.article.ArticleResponseModel
import javax.inject.Inject

class ModelToEntityMapper @Inject constructor(
    private val htmlUtil: HtmlUtil
) {

    fun toArticleEntity(articleResponseModel: ArticleResponseModel): ArticleEntity {
        return ArticleEntity(
            itemId = articleResponseModel.items.first().id,
            itemTitle = articleResponseModel.items.first().title,
            itemUpdatedMillisecond = articleResponseModel.items.first().updatedMillisecond,
            itemPublishedMillisecond = articleResponseModel.items.first().publishedMillisecond,
            author = articleResponseModel.items.first().author,
            content = articleResponseModel.items.first().summary.content,
            feedId = articleResponseModel.id,
            href = articleResponseModel.alternate.herf,
            previewImageUrl = htmlUtil
                .extractImageFromImgTags(rawHtmlString = articleResponseModel.items.first().summary.content)
        )
    }

}