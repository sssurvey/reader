package com.haomins.data_model.entity

import org.junit.Assert.assertTrue
import org.junit.Test

class SubscriptionEntityTest {

    @Test
    fun testInit() {
        val subscriptionEntity = SubscriptionEntity(
            id = "0",
            title = (99).toString(),
            sortId = "sortId: 99",
            firstItemMilSec = System.currentTimeMillis().toString(),
            url = "rss.99.com",
            htmlUrl = "www.99.com",
            iconUrl = "www.99.com/pic"
        )
        assertTrue(subscriptionEntity.id == "0")
        assertTrue(subscriptionEntity.title == "99")
        assertTrue(subscriptionEntity.sortId == "sortId: 99")
        assertTrue(subscriptionEntity.firstItemMilSec.isNotEmpty())
        assertTrue(subscriptionEntity.url == "rss.99.com")
        assertTrue(subscriptionEntity.htmlUrl == "www.99.com")
        assertTrue(subscriptionEntity.iconUrl == "www.99.com/pic")
    }

}