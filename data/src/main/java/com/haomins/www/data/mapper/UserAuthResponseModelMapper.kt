package com.haomins.www.data.mapper

import com.haomins.www.data.model.responses.user.UserAuthResponseModel
import javax.inject.Inject

class UserAuthResponseModelMapper @Inject constructor() : BaseDataToDomainMapper<UserAuthResponseModel, com.haomins.domain.model.UserAuthResponseModel> {

    override fun dataModelToDomainModel(dataModel: UserAuthResponseModel): com.haomins.domain.model.UserAuthResponseModel {
        return com.haomins.domain.model.UserAuthResponseModel(
                auth = dataModel.auth
        )
    }

    override fun domainModelToDataModel(domainModel: com.haomins.domain.model.UserAuthResponseModel): UserAuthResponseModel {
        TODO("Not yet implemented")
    }

}