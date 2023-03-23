package com.haomins.model.remote.subscription

import com.google.gson.Gson
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AddSourceResponseModelTest {

    private lateinit var gson: Gson
    private val testJsonException by lazy {
        "{\n" +
                "    \"query\": \"ff\",\n" +
                "    \"numResults\": 0,\n" +
                "    \"error\": \"No feeds found by that keyword or URL\"\n" +
                "}"
    }
    private val testJsonSuccess by lazy {
        "{\n" +
                "    \"query\": \"medium.com/feed/proandroiddev\",\n" +
                "    \"numResults\": 1,\n" +
                "    \"streamId\": \"feed/5eaa0ceefea0e760d80006b9\"\n" +
                "}"
    }

    @Before
    fun setup() {
        gson = Gson()
    }

    @Test
    fun testParseToClassSuccess() {
        val addSubscriptionResponseModel =
            gson.fromJson(testJsonSuccess, AddSourceResponseModel::class.java)
        assertTrue("medium.com/feed/proandroiddev" == addSubscriptionResponseModel.query)
    }

    @Test
    fun testParseToClassFail() {
        val addSubscriptionResponseModel =
            gson.fromJson(testJsonException, AddSourceResponseModel::class.java)
        assertTrue("No feeds found by that keyword or URL" == addSubscriptionResponseModel.error)
    }

    @Test
    fun testParseToJsonSuccess() {
        val validation =
            "{\"query\":\"medium.com/feed/proandroiddev\",\"numResults\":1,\"streamId\":\"feed/5eaa0ceefea0e760d80006b9\"}"
        val addSubscriptionResponseModel =
            gson.fromJson(testJsonSuccess, AddSourceResponseModel::class.java)
        assertTrue(validation == gson.toJson(addSubscriptionResponseModel))
    }

    @Test
    fun testParseToJsonFail() {
        val validation =
            "{\"query\":\"ff\",\"numResults\":0,\"error\":\"No feeds found by that keyword or URL\"}"
        val addSubscriptionResponseModel =
            gson.fromJson(testJsonException, AddSourceResponseModel::class.java)
        assertTrue(validation == gson.toJson(addSubscriptionResponseModel))
    }
}