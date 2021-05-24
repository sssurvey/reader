package com.haomins.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.haomins.data.db.dao.ArticleDao
import com.haomins.data.db.dao.SubscriptionDao
import com.haomins.data.model.entities.ArticleEntity
import com.haomins.data.model.entities.SubscriptionEntity

@Database(
        entities = [SubscriptionEntity::class, ArticleEntity::class],
        version = 1,
        exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "READER_DB"
    }

    abstract fun subscriptionDao(): SubscriptionDao

    abstract fun articleDao(): ArticleDao
}