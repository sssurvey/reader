package com.haomins.data.mapper

interface BaseDataToDomainMapper<DataModel, DomainModel> {

    fun dataModelToDomainModel(dataModel: DataModel): DomainModel

    fun domainModelToDataModel(domainModel: DomainModel): DataModel

}