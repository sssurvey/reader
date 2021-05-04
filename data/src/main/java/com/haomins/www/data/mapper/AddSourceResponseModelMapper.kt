package com.haomins.www.data.mapper

import javax.inject.Inject
import com.haomins.domain.model.AddSourceResponseModel as DomainModel
import com.haomins.www.data.model.responses.subscription.AddSourceResponseModel as DataModel

class AddSourceResponseModelMapper @Inject constructor() : BaseDataToDomainMapper<DataModel, DomainModel> {

    override fun dataModelToDomainModel(dataModel: DataModel): DomainModel {
        return DomainModel(
                result = dataModel.numResults,
                error = dataModel.error
        )
    }

    override fun domainModelToDataModel(domainModel: DomainModel): DataModel {
        TODO("Not yet implemented")
    }

}