package com.haomins.data.mapper.responsemapper

import com.haomins.data.mapper.BaseDataToDomainMapper
import javax.inject.Inject
import com.haomins.domain.model.responses.AddSourceResponseModel as DomainModel
import com.haomins.data.model.responses.subscription.AddSourceResponseModel as DataModel

class AddSourceResponseModelMapper @Inject constructor() :
    BaseDataToDomainMapper<DataModel, DomainModel> {

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