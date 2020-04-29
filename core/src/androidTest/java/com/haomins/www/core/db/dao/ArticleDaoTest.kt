package com.haomins.www.core.db.dao

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.haomins.www.core.data.entities.ArticleEntity
import com.haomins.www.core.db.AppDatabase
import junit.framework.Assert.assertTrue
import org.junit.After
import org.junit.Before
import org.junit.Test

class ArticleDaoTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var articleDao: ArticleDao

    @Before
    fun createDb() {
        val articleList: MutableList<ArticleEntity> = ArrayList()
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        articleDao = appDatabase.articleDao()

        // Create dummy articles
        for (counter in 0 until 100) {
            articleList.add(
                ArticleEntity(
                    itemId = "$counter",
                    feedId = "${counter * 2}",
                    itemTitle = "Test title",
                    itemUpdatedMillisecond = System.currentTimeMillis(),
                    itemPublishedMillisecond = System.currentTimeMillis(),
                    author = "Dr.StrangeLove",
                    content = "This is a test article, haha."
                )
            )
        }

        // Populate DB
        articleList.forEach { appDatabase.articleDao().insert(it) }
    }

    @After
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    fun testGetAll() {
        var counter = 0
        articleDao.getAll().blockingFirst().forEach {
            assertTrue(it.itemId == counter.toString())
            counter++
        }
    }
}