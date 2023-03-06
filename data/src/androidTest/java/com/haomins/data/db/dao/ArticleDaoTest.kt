package com.haomins.data.db.dao

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.haomins.data.db.AppDatabase
import com.haomins.data.model.entities.ArticleEntity
import org.junit.After
import org.junit.Assert.assertTrue
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
                    content = "This is a test article, haha.",
                    href = "",
                    previewImageUrl = ""
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
        articleDao.getAll().blockingGet().forEach {
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
        val originalSize = articleDao.getAll().blockingGet().size
        articleDao.insert(
            ArticleEntity(
                itemId = "$100",
                feedId = "${100 * 2}",
                itemTitle = "Test title",
                itemUpdatedMillisecond = System.currentTimeMillis(),
                itemPublishedMillisecond = System.currentTimeMillis(),
                author = "Dr.StrangeLove",
                content = "This is a test article, haha.",
                href = "",
                previewImageUrl = ""
            )
        )
        assertTrue(articleDao.getAll().blockingGet().size > originalSize)
    }

    @Test
    fun testSelectArticleByItemId() {
        assertTrue(articleDao.selectArticleByItemId("1").blockingGet().itemId == 1.toString())
    }

    @Test
    fun testClearTable() {
        assertTrue(articleDao.getAll().blockingGet().isNotEmpty())
        articleDao.clearTable()
        assertTrue(articleDao.getAll().blockingGet().isEmpty())
    }
}