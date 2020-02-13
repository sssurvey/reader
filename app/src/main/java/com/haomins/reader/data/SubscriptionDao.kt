package com.haomins.reader.data

import androidx.room.Dao
import androidx.room.Query
import com.haomins.reader.models.subscription.SubscriptionItemModel

@Dao
interface SubscriptionDao {

    @Query("SELECT * FROM subscriptionitemmodel")
    fun getAll(): List<SubscriptionItemModel>

}