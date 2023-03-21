package com.haomins.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.haomins.data.db.dao.ArticleDao
import com.haomins.data.db.dao.SubscriptionDao
import com.haomins.data_model.local.ArticleEntity
import com.haomins.data_model.local.SubscriptionEntity

@Database(
    entities = [SubscriptionEntity::class, ArticleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun subscriptionDao(): SubscriptionDao

    abstract fun articleDao(): ArticleDao

    companion object {

        private const val DATABASE_NAME = "READER_DB"

        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                DATABASE_NAME
            ).build()
        }

    }
}