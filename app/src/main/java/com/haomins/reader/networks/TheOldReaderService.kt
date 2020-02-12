package com.haomins.reader.networks

import com.haomins.reader.models.subscription.SubscriptionSourceListResponseModel
import com.haomins.reader.models.user.UserAuthResponseModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface TheOldReaderService {

    companion object {
        const val BASE_URL = "https://theoldreader.com/"
        const val AUTH_HEADER_VALUE_PREFIX = "GoogleLogin auth="
    }

    /**
     * Login user Post Request
     *
     * This end point allows you to login to the old reader userName and userPassword. When calling
     * this method, you should only provide two params.
     *
     * @param userEmail User's account name
     * @param userPassword User's password
     *
     * @return Single<UserAuthResponse>
     */
    @POST("accounts/ClientLogin")
    fun loginUser(
        @Query("Email") userEmail: String,
        @Query("Passwd") userPassword: String,
        @Query("output") output: String = "json",
        @Query("service") service: String = "reader",
        @Query("accountType") accountType: String = "HOSTED",
        @Query("client") client: String = "Reader"
    ): Single<UserAuthResponseModel>

    @GET("reader/api/0/subscription/list")
    fun loadSubscriptionSourceList(
        @Header("Authorization") headerAuthValue: String,
        @Query("output") output: String = "json"
    ): Single<SubscriptionSourceListResponseModel>
}