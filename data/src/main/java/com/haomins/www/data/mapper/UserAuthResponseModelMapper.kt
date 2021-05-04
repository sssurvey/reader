package com.haomins.www.data.mapper

import com.haomins.www.data.model.responses.user.UserAuthResponseModel

class UserAuthResponseModelMapper : BaseDataToDomainMapper<UserAuthResponseModel, com.haomins.domain.model.UserAuthResponseModel> {
    override fun dataModelToDomainModel(dataModel: UserAuthResponseModel): com.haomins.domain.model.UserAuthResponseModel {
        TODO("Not yet implemented")
    }

    override fun domainModelToDataModel(domainModel: com.haomins.domain.model.UserAuthResponseModel): UserAuthResponseModel {
        TODO("Not yet implemented")
    }
}