package com.haomins.model.remote.user

import com.google.gson.Gson
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class UserAuthResponseModelTest {

    private lateinit var gson: Gson
    private val testJson by lazy {
        "{\n" +
                "    \"SID\": \"none\",\n" +
                "    \"LSID\": \"none\",\n" +
                "    \"Auth\": \"ThIsIsrEalaUtH\"\n" +
                "}"
    }

    @Before
    fun setUp() {
        gson = Gson()
    }

    @Test
    fun testParseToClass() {
        val userAuthResponseModel = gson.fromJson(testJson, UserAuthResponseModel::class.java)
        assertTrue(userAuthResponseModel.auth == "ThIsIsrEalaUtH")
    }

    @Test
    fun testParseToJson() {
        val validation = "{\"SID\":\"none\",\"LSID\":\"none\",\"Auth\":\"ThIsIsrEalaUtH\"}"
        val userAuthResponseModel = gson.fromJson(testJson, UserAuthResponseModel::class.java)
        assertTrue(validation == gson.toJson(userAuthResponseModel))
    }
}