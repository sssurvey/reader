package com.haomins.data.mapper.entitymapper

import com.haomins.data.mapper.BaseDataToDomainMapper
import com.haomins.data_model.entity.SubscriptionEntity
import javax.inject.Inject

class SubscriptionEntityMapper @Inject constructor() :
    BaseDataToDomainMapper<SubscriptionEntity, com.haomins.domain_model.entities.SubscriptionEntity> {

    override fun dataModelToDomainModel(dataModel: SubscriptionEntity): com.haomins.domain_model.entities.SubscriptionEntity {
        return com.haomins.domain_model.entities.SubscriptionEntity(
            title = dataModel.title,
            iconUrl = dataModel.iconUrl,
            id = dataModel.id
        )
    }

    override fun domainModelToDataModel(domainModel: com.haomins.domain_model.entities.SubscriptionEntity): SubscriptionEntity {
        TODO("Not yet implemented")
    }

}