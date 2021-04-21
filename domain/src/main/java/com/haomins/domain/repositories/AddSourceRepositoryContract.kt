package com.haomins.domain.repositories

import com.haomins.domain.model.AddSourceResponseModel
import io.reactivex.Single

interface AddSourceRepositoryContract {

    fun addSource(source: String): Single<AddSourceResponseModel>

}