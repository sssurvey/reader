package com.haomins.reader.networks

import com.haomins.reader.models.user.UserAuthResponse
import io.reactivex.Single
import retrofit2.http.POST
import retrofit2.http.Query

interface TheOldReaderService {

    enum class Url(val string: String) {
        BASE_URL("https://theoldreader.com/"),
    }

    @POST("accounts/ClientLogin")
    fun loginUser(
        @Query("Email") userEmail: String,
        @Query("Passwd") userPassword: String,
        @Query("output") output: String = "json",
        @Query("service") service: String = "reader",
        @Query("accountType") accountType: String = "HOSTED",
        @Query("client") client: String = "Reader"
    ): Single<UserAuthResponse>
}