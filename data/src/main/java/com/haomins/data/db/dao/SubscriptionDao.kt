package com.haomins.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.haomins.data_model.local.SubscriptionEntity
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