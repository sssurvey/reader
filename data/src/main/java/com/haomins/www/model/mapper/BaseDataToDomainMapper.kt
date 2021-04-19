package com.haomins.www.model.mapper

abstract class BaseDataToDomainMapper<DataModel, DomainModel> {

    abstract fun dataModelToDomainModel(dataModel: DataModel): DomainModel

    abstract fun domainModelToDataModel(domainModel: DomainModel): DataModel

}