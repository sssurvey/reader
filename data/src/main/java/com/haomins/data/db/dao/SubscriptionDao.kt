package com.haomins.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.haomins.model.entity.SubscriptionEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface SubscriptionDao {

    @Query("DELETE FROM subscription_entity")
    fun clearTable(): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(sources: List<SubscriptionEntity>): Completable

    @Query("SELECT * FROM subscription_entity")
    fun getAll(): Single<List<SubscriptionEntity>>

}