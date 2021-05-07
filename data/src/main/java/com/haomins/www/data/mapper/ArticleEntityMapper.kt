package com.haomins.www.data.mapper

import com.haomins.www.data.model.entities.ArticleEntity
import javax.inject.Inject

class ArticleEntityMapper @Inject constructor() : BaseDataToDomainMapper<ArticleEntity, com.haomins.domain.model.entities.ArticleEntity> {

    override fun dataModelToDomainModel(dataModel: ArticleEntity): com.haomins.domain.model.entities.ArticleEntity {
        return com.haomins.domain.model.entities.ArticleEntity(
                itemTitle = dataModel.itemTitle,
                itemId = dataModel.itemId,
                author = dataModel.author,
                content = dataModel.content,
                itemPublishedMillisecond = dataModel.itemPublishedMillisecond,
                itemUpdatedMillisecond = dataModel.itemUpdatedMillisecond
        )
    }

    override fun domainModelToDataModel(domainModel: com.haomins.domain.model.entities.ArticleEntity): ArticleEntity {
        TODO("Not yet implemented")
    }
}