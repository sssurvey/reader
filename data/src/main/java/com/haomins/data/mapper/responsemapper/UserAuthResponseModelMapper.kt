package com.haomins.data.mapper.responsemapper

import com.haomins.data.mapper.BaseDataToDomainMapper
import com.haomins.data_model.remote.user.UserAuthResponseModel
import javax.inject.Inject

class UserAuthResponseModelMapper @Inject constructor() :
    BaseDataToDomainMapper<UserAuthResponseModel, com.haomins.domain_model.responses.UserAuthResponseModel> {

    override fun dataModelToDomainModel(dataModel: UserAuthResponseModel): com.haomins.domain_model.responses.UserAuthResponseModel {
        return com.haomins.domain_model.responses.UserAuthResponseModel(
            auth = dataModel.auth
        )
    }

    override fun domainModelToDataModel(domainModel: com.haomins.domain_model.responses.UserAuthResponseModel): UserAuthResponseModel {
        TODO("Not yet implemented")
    }

}