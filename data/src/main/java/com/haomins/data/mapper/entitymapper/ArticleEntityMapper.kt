package com.haomins.data.mapper.entitymapper

import com.haomins.data.mapper.BaseDataToDomainMapper
import com.haomins.data.model.entities.ArticleEntity
import com.haomins.data.util.DateUtils
import javax.inject.Inject

class ArticleEntityMapper @Inject constructor(
    private val dateUtils: DateUtils
) :
    BaseDataToDomainMapper<ArticleEntity, com.haomins.domain.model.entities.ArticleEntity> {

    override fun dataModelToDomainModel(dataModel: ArticleEntity): com.haomins.domain.model.entities.ArticleEntity {
        return com.haomins.domain.model.entities.ArticleEntity(
            itemTitle = dataModel.itemTitle,
            itemId = dataModel.itemId,
            author = dataModel.author,
            content = dataModel.content,
            howLongAgo = dateUtils.howLongAgo(dataModel.itemPublishedMillisecond),
            updatedTime = dateUtils.to24HrString(dataModel.itemUpdatedMillisecond),
            itemPublishedMillisecond = dataModel.itemPublishedMillisecond,
            itemUpdatedMillisecond = dataModel.itemUpdatedMillisecond
        )
    }

    override fun domainModelToDataModel(domainModel: com.haomins.domain.model.entities.ArticleEntity): ArticleEntity {
        TODO("Not yet implemented")
    }
}