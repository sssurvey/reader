package com.haomins.reader

import com.haomins.www.data.models.article.ArticleResponseModel
import com.haomins.www.data.models.article.ItemRefListResponseModel
import com.haomins.www.data.models.subscription.SubscriptionSourceListResponseModel
import com.haomins.www.data.models.user.UserAuthResponseModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface TheOldReaderService {

    companion object {
        const val EMPTY = ""
        const val BASE_URL = "https://theoldreader.com/"
        const val AUTH_HEADER_VALUE_PREFIX = "GoogleLogin auth="
        const val DEFAULT_PROTOCOL = "https:"
        const val DEFAULT_ARTICLE_AMOUNT = 25

        private const val DEFAULT_OUTPUT_FORMAT = "json"
        private const val DEFAULT_SERVICE_NAME = "reader"
        private const val DEFAULT_CLIENT_NAME = "Reader"
        private const val DEFAULT_ACCOUNT_TYPE = "HOSTED"
        private const val BASE_API = "reader/api/0/"
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
        @Query("output") output: String = DEFAULT_OUTPUT_FORMAT,
        @Query("service") service: String = DEFAULT_SERVICE_NAME,
        @Query("accountType") accountType: String = DEFAULT_ACCOUNT_TYPE,
        @Query("client") client: String = DEFAULT_CLIENT_NAME
    ): Single<UserAuthResponseModel>

    /**
     * Get user's subscription list
     *
     * This get request will allow you to get json data of all the RSS source list user subscribed.
     * The auth information of the user should be included in the Header of the request.
     *
     * @param headerAuthValue User's auth token and auth meta data
     *
     * @return Single<SubscriptionSourceListResponseModel>
     */
    @GET(BASE_API + "subscription/list")
    fun loadSubscriptionSourceList(
        @Header("Authorization") headerAuthValue: String,
        @Query("output") output: String = DEFAULT_OUTPUT_FORMAT
    ): Single<SubscriptionSourceListResponseModel>

    /**
     * Get article refs based on feed (id)
     *
     * This get request will allow you to get json data of all article references under the provided
     * feed, you can use the article `itemRef` to load more details about these articles.
     *
     * @param headerAuthValue User's auth token and auth meta data
     * @param feedId Feed ID for the source feed, all article returned belongs to this Feed ID
     * @param continueLoad An ID for continue load after the first N amount (specified in 'n=')
     *
     * @return Single<ItemRefListResponseModel>
     */
    @GET(BASE_API + "stream/items/ids")
    fun loadArticleListByFeed(
        @Header("Authorization") headerAuthValue: String,
        @Query("s") feedId: String,
        @Query("n") articleAmount: String = DEFAULT_ARTICLE_AMOUNT.toString(),
        @Query("c") continueLoad: String = EMPTY,
        @Query("output") output: String = DEFAULT_OUTPUT_FORMAT
    ): Single<ItemRefListResponseModel>

    /**
     * Get the article details and contents
     *
     * This get request will allow you get all the information for an article item via refItem id
     * each refItem id is linked to exactly one article.
     *
     * @param headerAuthValue User's auth token and auth meta data
     * @param i ref item ID that we can use to find the article
     *
     * @return
     */
    @GET(BASE_API + "stream/items/contents")
    fun loadArticleDetailsByRefId(
        @Header("Authorization") headerAuthValue: String,
        @Query("i") refItemId: String,
        @Query("output") output: String = DEFAULT_OUTPUT_FORMAT
    ): Single<ArticleResponseModel>
}