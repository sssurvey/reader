package com.haomins.domain.repositories

import io.reactivex.Single

interface DisclosureRepositoryContract {

    fun loadDisclosureContent(): Single<String>

}