package com.haomins.data.mapper.responsemapper

import com.haomins.data.mapper.BaseDataToDomainMapper
import com.haomins.data_model.remote.subscription.SubscriptionSourceListResponseModel
import javax.inject.Inject

class SubscriptionSourceListResponseModelMapper @Inject constructor() :
    BaseDataToDomainMapper<SubscriptionSourceListResponseModel, com.haomins.domain.model.responses.SubscriptionSourceListResponseModel> {

    override fun dataModelToDomainModel(dataModel: SubscriptionSourceListResponseModel):
            com.haomins.domain.model.responses.SubscriptionSourceListResponseModel {
        return com.haomins.domain.model.responses.SubscriptionSourceListResponseModel(
            subscriptions = ArrayList<com.haomins.domain.model.responses.SubscriptionItemModel>().apply {
                dataModel.subscriptions.forEach {
                    com.haomins.domain.model.responses.SubscriptionItemModel(
                        id = it.id,
                        title = it.title,
                        categories = it.categories.map {
                            com.haomins.domain.model.responses.Category(
                                id = it.id,
                                label = it.label
                            )
                        }.toTypedArray(),
                        sortId = it.sortId,
                        firstItemMilSec = it.firstItemMilSec,
                        url = it.url,
                        htmlUrl = it.htmlUrl,
                        iconUrl = it.iconUrl
                    )
                }
            }
        )
    }

    override fun domainModelToDataModel(domainModel: com.haomins.domain.model.responses.SubscriptionSourceListResponseModel):
            SubscriptionSourceListResponseModel {
        TODO("Not yet implemented")
    }

}