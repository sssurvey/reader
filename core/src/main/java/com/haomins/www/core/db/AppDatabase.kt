package com.haomins.www.core.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.haomins.www.core.data.entities.ArticleEntity
import com.haomins.www.core.data.entities.SubscriptionEntity
import com.haomins.www.core.db.dao.ArticleDao
import com.haomins.www.core.db.dao.SubscriptionDao

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