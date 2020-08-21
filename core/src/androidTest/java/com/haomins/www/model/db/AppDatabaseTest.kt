package com.haomins.www.model.db

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.haomins.www.model.db.dao.ArticleDao
import com.haomins.www.model.db.dao.SubscriptionDao
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AppDatabaseTest {

    private lateinit var appDatabase: AppDatabase
    private var subscriptionDao: SubscriptionDao? = null
    private var articleDao: ArticleDao? = null

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        subscriptionDao = appDatabase.subscriptionDao()
        articleDao = appDatabase.articleDao()
    }

    @After
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    fun testGetSubscriptionDao() {
        assertTrue(subscriptionDao != null)
    }

    @Test
    fun testGetArticleDao() {
        assertTrue(articleDao != null)
    }
}