package com.haomins.www.data.model.responses.article

import com.google.gson.Gson
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ArticleResponseModelTest {

    private lateinit var gson: Gson
    private val testJson by lazy {
        "{\n" +
                "    \"direction\": \"ltr\",\n" +
                "    \"id\": \"feed/5b902c06fea0e7da48000b28\",\n" +
                "    \"title\": \"Ars Technica\",\n" +
                "    \"description\": \"\",\n" +
                "    \"self\": {\n" +
                "        \"href\": \"https://theoldreader.com/reader/api/0/stream/items/contents?i=5ea7352e7eb3db3e090034a4&output=json\"\n" +
                "    },\n" +
                "    \"alternate\": {\n" +
                "        \"href\": \"https://arstechnica.com\",\n" +
                "        \"type\": \"text/html\"\n" +
                "    },\n" +
                "    \"updated\": 1588197426,\n" +
                "    \"items\": [\n" +
                "        {\n" +
                "            \"crawlTimeMsec\": \"1588016398957\",\n" +
                "            \"timestampUsec\": \"1588016219000000\",\n" +
                "            \"id\": \"tag:google.com,2005:reader/item/5ea7352e7eb3db3e090034a4\",\n" +
                "            \"categories\": [\n" +
                "                \"user/-/state/com.google/reading-list\",\n" +
                "                \"user/-/state/com.google/fresh\"\n" +
                "            ],\n" +
                "            \"title\": \"We’ve found the world’s worst coworker, and here’s what they do\",\n" +
                "            \"published\": 1588016219,\n" +
                "            \"updated\": 1588016219,\n" +
                "            \"tor_identifier\": \"ff1d855dcbc33e556f142b29_5ea7352e7eb3db3e090034a4\",\n" +
                "            \"canonical\": [\n" +
                "                {\n" +
                "                    \"href\": \"https://arstechnica.com/?p=1671397\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"alternate\": [\n" +
                "                {\n" +
                "                    \"href\": \"https://arstechnica.com/?p=1671397\",\n" +
                "                    \"type\": \"text/html\"\n" +
                "                }\n" +
                "            ],\n" +
                "            \"summary\": {\n" +
                "                \"direction\": \"ltr\",\n" +
                "                \"content\": \"<div>\\n<figure><img src=\\\"https://cdn.arstechnica.net/wp-content/uploads/2020/04/GettyImages-984023026-800x533.jpg\\\" alt=\\\"Young businesswoman having fun while pretending to be a clown on a meeting in the office. The view is through glass.\\\"><p><a href=\\\"https://cdn.arstechnica.net/wp-content/uploads/2020/04/GettyImages-984023026.jpg\\\">Enlarge</a> / Does every organization have one of them? (credit: <a href=\\\"https://www.gettyimages.com/search/photographer?family=editorial&amp;mediatype=photography&amp;phrase=mark%20wilson&amp;photographer=mark%20wilson&amp;sort=mostpopular\\\">Getty Images</a>)</p>  </figure><div><a></a></div>\\n<p>You know it's bad when Senior Technology Editor <a href=\\\"https://arstechnica.com/author/lee-hutchinson/\\\">Lee Hutchinson</a> messages you on Slack and says, \\\"<a href=\\\"https://old.reddit.com/r/AmItheAsshole/comments/g6qw2f/aita_for_ccing_the_ceo_on_emails_for_my_coworkers/\\\">Hey, check out this Reddit thread</a>.\\\" Since the alternative was finishing the transcription of an interview, I did indeed check out that Reddit thread. Oh boy. Posted in r/AmItheAsshole—protip, if you have to ask, the answer is usually \\\"yes\\\"—the thread centers on the tardy replies the poster receives to the emails he sends out. His solution? Simple: he now CCs the company CEO on any email that goes to a coworker he thinks might not respond the instant it hits their inbox.</p>\\n<p>\\\"Ahh,\\\" you might be thinking. \\\"At a small mom-and-pop shop, that's a little annoying but probably OK, because everyone knows everyone.\\\" Well, think again, dear reader, for this fine human being has decided to implement his one-man \\\"email the big boss\\\" policy at a company with more than 10,000 employees.</p>\\n<p>Part of his problem, it seems, is a general contempt for fellow employees who work in IT:</p>\\n</div><p><a href=\\\"https://arstechnica.com/?p=1671397#p3\\\">Read 5 remaining paragraphs</a> | <a href=\\\"https://arstechnica.com/?p=1671397&amp;comments=1\\\">Comments</a></p><div>\\n<a href=\\\"http://feeds.arstechnica.com/~ff/arstechnica/index?a=5eo7h2Q_zKY:QIfeOsLqMUA:V_sGLiPBpWU\\\"><img src=\\\"http://feeds.feedburner.com/~ff/arstechnica/index?i=5eo7h2Q_zKY:QIfeOsLqMUA:V_sGLiPBpWU\\\"></a> <a href=\\\"http://feeds.arstechnica.com/~ff/arstechnica/index?a=5eo7h2Q_zKY:QIfeOsLqMUA:F7zBnMyn0Lo\\\"><img src=\\\"http://feeds.feedburner.com/~ff/arstechnica/index?i=5eo7h2Q_zKY:QIfeOsLqMUA:F7zBnMyn0Lo\\\"></a> <a href=\\\"http://feeds.arstechnica.com/~ff/arstechnica/index?a=5eo7h2Q_zKY:QIfeOsLqMUA:qj6IDK7rITs\\\"><img src=\\\"http://feeds.feedburner.com/~ff/arstechnica/index?d=qj6IDK7rITs\\\"></a> <a href=\\\"http://feeds.arstechnica.com/~ff/arstechnica/index?a=5eo7h2Q_zKY:QIfeOsLqMUA:yIl2AUoC8zA\\\"><img src=\\\"http://feeds.feedburner.com/~ff/arstechnica/index?d=yIl2AUoC8zA\\\"></a>\\n</div>\"\n" +
                "            },\n" +
                "            \"author\": \"Jonathan M. Gitlin\",\n" +
                "            \"annotations\": [],\n" +
                "            \"likingUsers\": [],\n" +
                "            \"likingUsersCount\": 0,\n" +
                "            \"comments\": [],\n" +
                "            \"origin\": {\n" +
                "                \"streamId\": \"feed/5b902c06fea0e7da48000b28\",\n" +
                "                \"title\": \"Ars Technica\",\n" +
                "                \"htmlUrl\": \"https://arstechnica.com\"\n" +
                "            }\n" +
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
        val articleResponseModel = gson.fromJson(testJson, ArticleResponseModel::class.java)
        assertTrue(articleResponseModel.id.isNotEmpty())
    }

    @Test
    fun testParseToJson() {
        val validateJson = "{\"direction\":\"ltr\",\"id\":\"feed/5b902c06fea0e7da48000b28\"," +
                "\"title\":\"Ars Technica\",\"description\":\"\",\"updated\":1588197426," +
                "\"items\":[{\"crawlTimeMsec\":\"1588016398957\",\"timestampUsec\":\"1588016219000000\"," +
                "\"id\":\"tag:google.com,2005:reader/item/5ea7352e7eb3db3e090034a4\"," +
                "\"categories\":[\"user/-/state/com.google/reading-list\",\"user/-/state/com.google/fresh\"]," +
                "\"title\":\"We’ve found the world’s worst coworker, and here’s what they do\"," +
                "\"summary\":{\"direction\":\"ltr\",\"content\":\"\\u003cdiv\\u003e\\n\\u003cfigure\\u003e\\" +
                "u003cimg src\\u003d\\\"https://cdn.arstechnica.net/wp-content/uploads/2020/04/GettyImages-984023026-800x533.jpg" +
                "\\\" alt\\u003d\\\"Young businesswoman having fun while pretending to be a clown on a meeting in the office. " +
                "The view is through glass.\\\"\\u003e\\u003cp\\u003e\\u003ca href\\u003d\\\"" +
                "https://cdn.arstechnica.net/wp-content/uploads/2020/04/GettyImages-984023026.jpg\\\"" +
                "\\u003eEnlarge\\u003c/a\\u003e / Does every organization have one of them? " +
                "(credit: \\u003ca href\\u003d\\\"https://www.gettyimages.com/search/photographer?family" +
                "\\u003deditorial\\u0026amp;mediatype\\u003dphotography\\u0026amp;phrase\\u003dmark%20wilson" +
                "\\u0026amp;photographer\\u003dmark%20wilson\\u0026amp;sort\\u003dmostpopular\\\"" +
                "\\u003eGetty Images\\u003c/a\\u003e)\\u003c/p\\u003e  \\u003c/figure\\u003e\\u003cdiv" +
                "\\u003e\\u003ca\\u003e\\u003c/a\\u003e\\u003c/div\\u003e\\n\\u003cp\\u003eYou know it" +
                "\\u0027s bad when Senior Technology Editor \\u003ca href\\u003d\\\"" +
                "https://arstechnica.com/author/lee-hutchinson/\\\"\\u003eLee Hutchinson" +
                "\\u003c/a\\u003e messages you on Slack and says, \\\"\\u003ca href\\u003d" +
                "\\\"https://old.reddit.com/r/AmItheAsshole/comments/g6qw2f/aita_for_ccing_the_ceo_on_emails_for_my_coworkers/" +
                "\\\"\\u003eHey, check out this Reddit thread\\u003c/a\\u003e.\\\" Since the alternative was finishing the " +
                "transcription of an interview, I did indeed check out that Reddit thread. Oh boy. Posted in r/AmItheAsshole—protip, " +
                "if you have to ask, the answer is usually \\\"yes\\\"—the thread centers on the tardy replies the poster receives " +
                "to the emails he sends out. His solution? Simple: he now CCs the company CEO on any email that goes to a coworker " +
                "he thinks might not respond the instant it hits their inbox.\\u003c/p\\u003e\\n\\u003cp\\u003e\\\"Ahh,\\\" " +
                "you might be thinking. \\\"At a small mom-and-pop shop, that\\u0027s a little annoying but probably OK, " +
                "because everyone knows everyone.\\\" Well, think again, dear reader, for this fine human being has decided to implement his one-man \\\"" +
                "email the big boss\\\" policy at a company with more than 10,000 employees.\\u003c/p\\u003e\\n\\u003cp\\u003ePart of his problem, " +
                "it seems, is a general contempt for fellow employees who work in IT:\\u003c/p\\u003e\\n\\u003c/div\\u003e\\u003cp\\u003e\\u003ca " +
                "href\\u003d\\\"https://arstechnica.com/?p\\u003d1671397#p3\\\"\\u003eRead 5 remaining paragraphs\\u003c/a\\u003e " +
                "| \\u003ca href\\u003d\\\"https://arstechnica.com/?p\\u003d1671397\\u0026amp;comments\\u003d1\\\"" +
                "\\u003eComments\\u003c/a\\u003e\\u003c/p\\u003e\\u003cdiv\\u003e\\n\\u003ca href\\u003d\\\"" +
                "http://feeds.arstechnica.com/~ff/arstechnica/index?a\\u003d5eo7h2Q_zKY:QIfeOsLqMUA:V_sGLiPBpWU\\\"" +
                "\\u003e\\u003cimg src\\u003d\\\"http://feeds.feedburner.com/~ff/arstechnica/index?i" +
                "\\u003d5eo7h2Q_zKY:QIfeOsLqMUA:V_sGLiPBpWU\\\"\\u003e\\u003c/a\\u003e \\u003ca " +
                "href\\u003d\\\"http://feeds.arstechnica.com/~ff/arstechnica/index?a\\u003d5eo7h2Q_zKY:QIfeOsLqMUA:F7zBnMyn0Lo\\\"" +
                "\\u003e\\u003cimg src\\u003d\\\"http://feeds.feedburner.com/~ff/arstechnica/index?i\\u003d5eo7h2Q_zKY:QIfeOsLqMUA:F7zBnMyn0Lo\\\"" +
                "\\u003e\\u003c/a\\u003e \\u003ca href\\u003d\\\"http://feeds.arstechnica.com/~ff/arstechnica/index?a\\u003d5eo7h2Q_zKY:QIfeOsLqMUA:qj6IDK7rITs\\\"" +
                "\\u003e\\u003cimg src\\u003d\\\"http://feeds.feedburner.com/~ff/arstechnica/index?d\\u003dqj6IDK7rITs\\\"\\u003e\\u003c/a\\u003e \\u003ca href\\u003d" +
                "\\\"http://feeds.arstechnica.com/~ff/arstechnica/index?a\\u003d5eo7h2Q_zKY:QIfeOsLqMUA:yIl2AUoC8zA\\\"\\u003e\\u003cimg src\\u003d\\\"" +
                "http://feeds.feedburner.com/~ff/arstechnica/index?d\\u003dyIl2AUoC8zA\\\"\\u003e\\u003c/a\\u003e\\n\\u003c/div\\u003e\"},\"published\":1588016219," +
                "\"updated\":1588016219,\"author\":\"Jonathan M. Gitlin\"}],\"likingUsersCount\":0}"
        val articleResponseModel = gson.fromJson(testJson, ArticleResponseModel::class.java)
        val jsonString = gson.toJson(articleResponseModel)
        assertTrue(jsonString.trim() == validateJson.trim())
    }

}