package com.haomins.domain

import com.haomins.model.entity.SubscriptionEntity
import com.haomins.model.remote.subscription.SubscriptionItemModel
import com.haomins.model.remote.subscription.SubscriptionListResponseModel
import org.jetbrains.annotations.TestOnly
import java.util.*

object MockGenerator {

    @TestOnly
    fun generateSubscriptionListResponseModel(): SubscriptionListResponseModel {
        val subscriptionsList = mutableListOf<SubscriptionItemModel>()
        for (i in 0 until 100) {
            subscriptionsList
                .add(
                    SubscriptionItemModel(
                        id = "testIdOf::$i",
                        title = "Test Rss Source $i",
                        categories = arrayOf(),
                        sortId = "${i + i}",
                        firstItemMilSec = Calendar.getInstance().time.toString(),
                        url = "https://rss.testdata.com",
                        htmlUrl = "https://rss.testdataABC.com",
                        iconUrl = "https://www.testdataABC.com/image.img"
                    )
                )
        }
        return SubscriptionListResponseModel(
            subscriptions = ArrayList(subscriptionsList)
        )
    }

    @TestOnly
    fun generateSubscriptionEntity(): List<SubscriptionEntity> {
        val subscriptionsList = mutableListOf<SubscriptionEntity>()
        for (i in 0 until 100) {
            subscriptionsList
                .add(
                    SubscriptionEntity(
                        id = "testIdOf::$i",
                        title = "Test Rss Source $i",
                        categories = arrayOf(),
                        sortId = "${i + i}",
                        firstItemMilSec = Calendar.getInstance().time.toString(),
                        url = "https://rss.testdata.com",
                        htmlUrl = "https://rss.testdataABC.com",
                        iconUrl = "https://www.testdataABC.com/image.img"
                    )
                )
        }
        return subscriptionsList
    }


}