package com.haomins.domain.repositories

import io.reactivex.Single

interface DisclosureLocalRepository {

    fun loadDisclosureContent(): Single<String>

}