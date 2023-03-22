package com.haomins.domain.repositories

import com.haomins.model.remote.subscription.AddSourceResponseModel
import io.reactivex.Single

interface AddSourceRepositoryContract {

    fun addSource(source: String): Single<AddSourceResponseModel>

}