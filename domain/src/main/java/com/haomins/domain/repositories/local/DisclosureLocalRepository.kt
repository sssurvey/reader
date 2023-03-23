package com.haomins.domain.repositories.local

import io.reactivex.Single

interface DisclosureLocalRepository {

    fun loadDisclosureContent(): Single<String>

}