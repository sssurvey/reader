package com.haomins.www.core.data.entities

import org.junit.Assert.assertTrue
import org.junit.Test

class ArticleEntityTest {

    @Test
    fun testInit() {
        val articleEntity = ArticleEntity(
            itemId = "1",
            feedId = "f1",
            itemTitle = "1 f1",
            itemUpdatedMillisecond = System.currentTimeMillis(),
            itemPublishedMillisecond = System.currentTimeMillis(),
            author = "Test Tester",
            content = "Unit test tester"
        )
        assertTrue(articleEntity.itemId == "1")
        assertTrue(articleEntity.feedId == "f1")
        assertTrue(articleEntity.itemTitle == "1 f1")
        assertTrue(articleEntity.itemUpdatedMillisecond > 0L)
        assertTrue(articleEntity.itemPublishedMillisecond > 0L)
        assertTrue(articleEntity.author == "Test Tester")
        assertTrue(articleEntity.content == "Unit test tester")
    }

}