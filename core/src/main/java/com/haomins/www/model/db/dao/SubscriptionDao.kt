package com.haomins.www.model.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.haomins.www.model.data.entities.SubscriptionEntity
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