package com.haomins.www.model.service

import android.app.Application
import androidx.room.Room
import com.haomins.www.model.db.AppDatabase
import com.haomins.www.model.db.dao.ArticleDao
import com.haomins.www.model.db.dao.SubscriptionDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RoomService @Inject constructor(application: Application) {

    private val roomDatabase by lazy {
        Room.databaseBuilder(application, AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            .build()
    }

    fun subscriptionDao(): SubscriptionDao {
        return roomDatabase.subscriptionDao()
    }

    fun articleDao(): ArticleDao {
        return roomDatabase.articleDao()
    }

}