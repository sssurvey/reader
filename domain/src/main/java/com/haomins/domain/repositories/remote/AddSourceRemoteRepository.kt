package com.haomins.domain.repositories.remote

import com.haomins.model.remote.subscription.AddSourceResponseModel
import io.reactivex.Single

interface AddSourceRemoteRepository {

    fun addSource(source: String): Single<AddSourceResponseModel>

}