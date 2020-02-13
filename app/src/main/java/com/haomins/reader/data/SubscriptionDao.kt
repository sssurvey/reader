package com.haomins.reader.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.haomins.reader.data.tables.SubscriptionEntity

@Dao
interface SubscriptionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg subscriptionEntities: SubscriptionEntity)

    @Query("SELECT * FROM subscription_entity")
    fun getAll(): LiveData<List<SubscriptionEntity>>
    
}