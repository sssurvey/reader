package com.haomins.reader.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface SubscriptionDao {

    @Query("SELECT * FROM subscription_entity")
    fun getAll(): LiveData<List<SubscriptionEntity>>

}