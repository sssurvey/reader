package com.haomins.data.mapper.responsemapper

import com.haomins.data.mapper.BaseDataToDomainMapper
import javax.inject.Inject
import com.haomins.model.remote.subscription.AddSourceResponseModel as DataModel
import com.haomins.domain_model.responses.AddSourceResponseModel as DomainModel

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