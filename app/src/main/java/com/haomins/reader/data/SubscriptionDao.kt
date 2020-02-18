package com.haomins.reader.data

import androidx.room.*
import com.haomins.reader.data.entities.SubscriptionEntity
import io.reactivex.Single

@Dao
interface SubscriptionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg subscriptionEntities: SubscriptionEntity)

    @Query("DELETE FROM subscription_entity")
    fun clearTable()

    @Query("SELECT * FROM subscription_entity")
    fun getAll(): Single<List<SubscriptionEntity>>
    
}