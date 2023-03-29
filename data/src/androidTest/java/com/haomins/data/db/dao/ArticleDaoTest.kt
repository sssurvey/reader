package com.haomins.data.db.dao

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.haomins.data.db.AppDatabase
import com.haomins.model.entity.ArticleEntity
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
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
        appDatabase.articleDao().insert(articleList).blockingAwait()
    }

    @After
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    fun testSelectAllArticleByFeedId() {
        assertTrue(articleDao.selectArticleByItemId("0").blockingGet().feedId == "0")
    }

    @Test
    fun testSelectArticleByItemId() {
        assertTrue(articleDao.selectArticleByItemId("1").blockingGet().itemId == 1.toString())
    }

}