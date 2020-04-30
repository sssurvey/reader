package com.haomins.www.core.data.models.subscription

import com.google.gson.Gson
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SubscriptionSourceListResponseModelTest {

    private lateinit var gson: Gson
    private val testJson by lazy {
        "{\n" +
                "    \"subscriptions\": [\n" +
                "        {\n" +
                "            \"id\": \"feed/5ac9a44bfea0e71e47000627\",\n" +
                "            \"title\": \"TechCrunch\",\n" +
                "            \"categories\": [],\n" +
                "            \"sortid\": \"5ac9a44bfea0e71e47000627\",\n" +
                "            \"firstitemmsec\": \"1588199885000\",\n" +
                "            \"url\": \"http://feeds.feedburner.com/Techcrunch\",\n" +
                "            \"htmlUrl\": \"https://techcrunch.com\",\n" +
                "            \"iconUrl\": \"//s.theoldreader.com/system/uploads/feed/picture/50e2/ea0d/bd92/79ba/8400/icon_124c.ico\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"feed/56e77decc70bc2091b0003d2\",\n" +
                "            \"title\": \"Slashdot\",\n" +
                "            \"categories\": [],\n" +
                "            \"sortid\": \"56e77decc70bc2091b0003d2\",\n" +
                "            \"firstitemmsec\": \"1588200297000\",\n" +
                "            \"url\": \"http://rss.slashdot.org/Slashdot/slashdot\",\n" +
                "            \"htmlUrl\": \"https://slashdot.org/\",\n" +
                "            \"iconUrl\": \"//s.theoldreader.com/system/uploads/feed/picture/50e2/ea0b/e721/ec6e/2800/icon_032a.ico\"\n" +
                "        }" +
                "    ]\n" +
                "}"
    }

    @Before
    fun setup() {
        gson = Gson()
    }

    @Test
    fun testParseToClass() {
        val subscriptionResponseModel = gson.fromJson(testJson, SubscriptionSourceListResponseModel::class.java)
        assertTrue("feed/5ac9a44bfea0e71e47000627" == subscriptionResponseModel.subscriptions[0].id)
    }

    @Test
    fun testParseToJson() {
        val validation = "{\"subscriptions\":[{\"id\":\"feed/5ac9a44bfea0e71e47000627\",\"title\":\"TechCrunch\"," +
                "\"categories\":[],\"sortid\":\"5ac9a44bfea0e71e47000627\",\"firstitemmsec\":\"1588199885000\"," +
                "\"url\":\"http://feeds.feedburner.com/Techcrunch\",\"htmlUrl\":\"https://techcrunch.com\"," +
                "\"iconUrl\":\"//s.theoldreader.com/system/uploads/feed/picture/50e2/ea0d/bd92/79ba/8400/icon_124c.ico\"}" +
                ",{\"id\":\"feed/56e77decc70bc2091b0003d2\",\"title\":\"Slashdot\",\"categories\":[],\"sortid\":\"56e77decc70bc2091b0003d2\"," +
                "\"firstitemmsec\":\"1588200297000\",\"url\":\"http://rss.slashdot.org/Slashdot/slashdot\",\"htmlUrl\":\"https://slashdot.org/\"," +
                "\"iconUrl\":\"//s.theoldreader.com/system/uploads/feed/picture/50e2/ea0b/e721/ec6e/2800/icon_032a.ico\"}]}"
        val subscriptionResponseModel = gson.fromJson(testJson, SubscriptionSourceListResponseModel::class.java)
        assertTrue(validation == gson.toJson(subscriptionResponseModel))
    }
}