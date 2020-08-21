package com.haomins.www.model.data.models.article

import com.google.gson.Gson
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ItemRefListResponseModelTest {

    private lateinit var gson: Gson
    private val jsonString by lazy {
        "{\n" +
                "    \"itemRefs\": [\n" +
                "        {\n" +
                "            \"id\": \"5e286f06175ad672e1006ad7\",\n" +
                "            \"directStreamIds\": [],\n" +
                "            \"timestampUsec\": \"1579708139085000\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"5cc338177eb3dba20c0060ec\",\n" +
                "            \"directStreamIds\": [],\n" +
                "            \"timestampUsec\": \"1556297736533000\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"5b6d980bca9f4055360046a8\",\n" +
                "            \"directStreamIds\": [],\n" +
                "            \"timestampUsec\": \"1533908983087000\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"5b0eff5e315ade8cf0000621\",\n" +
                "            \"directStreamIds\": [],\n" +
                "            \"timestampUsec\": \"1527710883293000\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"5af33597175ad6b2e3005519\",\n" +
                "            \"directStreamIds\": [],\n" +
                "            \"timestampUsec\": \"1525888386389000\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"5acb6b70511dbef82f01c745\",\n" +
                "            \"directStreamIds\": [],\n" +
                "            \"timestampUsec\": \"1523280729605000\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"5ac9a44bfea0e71e47000628\",\n" +
                "            \"directStreamIds\": [],\n" +
                "            \"timestampUsec\": \"1523138693000000\"\n" +
                "        }\n" +
                "    ]\n" +
                "}"
    }

    @Before
    fun setup() {
        gson = Gson()
    }

    @Test
    fun testParseToClass() {
        val itemRefListResponseModel =
            gson.fromJson(jsonString, ItemRefListResponseModel::class.java)
        assertTrue("5e286f06175ad672e1006ad7" == itemRefListResponseModel.itemRefs[0].id)
    }

    @Test
    fun testParseToJson() {
        val validation = "{\"itemRefs\":[{\"id\":\"5e286f06175ad672e1006ad7\"," +
                "\"directStreamIds\":[],\"timestampUsec\":\"1579708139085000\"}," +
                "{\"id\":\"5cc338177eb3dba20c0060ec\",\"directStreamIds\":[]," +
                "\"timestampUsec\":\"1556297736533000\"},{\"id\":\"5b6d980bca9f4055360046a8\"," +
                "\"directStreamIds\":[],\"timestampUsec\":\"1533908983087000\"}," +
                "{\"id\":\"5b0eff5e315ade8cf0000621\",\"directStreamIds\":[]," +
                "\"timestampUsec\":\"1527710883293000\"},{\"id\":\"5af33597175ad6b2e3005519\"," +
                "\"directStreamIds\":[],\"timestampUsec\":\"1525888386389000\"}," +
                "{\"id\":\"5acb6b70511dbef82f01c745\",\"directStreamIds\":[]," +
                "\"timestampUsec\":\"1523280729605000\"},{\"id\":\"5ac9a44bfea0e71e47000628\"," +
                "\"directStreamIds\":[],\"timestampUsec\":\"1523138693000000\"}]}"
        val itemRefListResponseModel =
            gson.fromJson(jsonString, ItemRefListResponseModel::class.java)
        val jsonString = gson.toJson(itemRefListResponseModel)
        assertTrue(validation == jsonString)
    }
}