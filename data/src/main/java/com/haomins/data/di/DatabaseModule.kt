package com.haomins.data.di

import android.content.Context
import androidx.room.RoomDatabase
import com.haomins.data.db.AppDatabase
import com.haomins.data.db.dao.ArticleDao
import com.haomins.data.db.dao.SubscriptionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideSubscriptionDao(appDatabase: AppDatabase): SubscriptionDao {
        return appDatabase.subscriptionDao()
    }

    @Provides
    fun provideArticleDao(appDatabase: AppDatabase): ArticleDao {
        return appDatabase.articleDao()
    }

}