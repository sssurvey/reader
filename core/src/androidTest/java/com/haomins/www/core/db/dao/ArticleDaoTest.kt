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

    @Test
    fun testSelectAllArticleByFeedId() {
        assertTrue(articleDao.selectArticleByItemId("0").blockingGet().feedId == "0")
    }

    @Test
    fun testInsert() {
        val originalSize = articleDao.getAll().blockingFirst().size
        articleDao.insert(
            ArticleEntity(
                itemId = "$100",
                feedId = "${100 * 2}",
                itemTitle = "Test title",
                itemUpdatedMillisecond = System.currentTimeMillis(),
                itemPublishedMillisecond = System.currentTimeMillis(),
                author = "Dr.StrangeLove",
                content = "This is a test article, haha."
            )
        )
        assertTrue(articleDao.getAll().blockingFirst().size > originalSize)
    }

    @Test
    fun testSelectArticleByItemId() {
        assertTrue(articleDao.selectArticleByItemId("1").blockingGet().itemId == 1.toString())
    }

    @Test
    fun testClearTable() {
        assertTrue(articleDao.getAll().blockingFirst().isNotEmpty())
        articleDao.clearTable()
        assertTrue(articleDao.getAll().blockingFirst().isEmpty())
    }
}