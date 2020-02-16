package com.haomins.reader.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.haomins.reader.data.entities.ArticleEntity
import com.haomins.reader.data.entities.SubscriptionEntity

@Database(entities = [SubscriptionEntity::class, ArticleEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "READER_DB"
    }

    abstract fun subscriptionDao(): SubscriptionDao

    abstract fun articleDao(): ArticleDao
}