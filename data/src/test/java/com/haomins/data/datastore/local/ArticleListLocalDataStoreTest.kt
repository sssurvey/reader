package com.haomins.data.datastore.local

import com.haomins.data.db.dao.ArticleDao
import com.haomins.model.entity.ArticleEntity
import io.reactivex.Completable
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class ArticleListLocalDataStoreTest {

    @Mock
    lateinit var articleDao: ArticleDao
    private lateinit var articleListLocalDataStore: ArticleListLocalDataStore

    private val mockEntities = listOf(
        ArticleEntity(
            "test_id",
            "test_feed_id",
            "test",
            0,
            0,
            "test_author",
            "test_content",
            "link",
            "link"
        )
    )

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        `when`(articleDao.insert(mockEntities)).thenReturn(Completable.complete())
        `when`(articleDao.clearTable()).thenReturn(Completable.complete())
        articleListLocalDataStore = ArticleListLocalDataStore(
            articleDao
        )
    }

    @Test
    fun `articleListLocalDataStore should initialize correctly`() {
        assertTrue(this::articleListLocalDataStore.isInitialized)
    }

    @Test
    fun `saveAllArticles should invoke correct classes`() {
        articleListLocalDataStore.saveAllArticles(mockEntities).subscribe()
        verify(articleDao, times(1)).insert(mockEntities)
    }

    @Test
    fun `loadAllArticles should invoke correct classes`() {
        articleListLocalDataStore.loadAllArticles()
        verify(articleDao, times(1)).getAll()
    }

    @Test
    fun `loadAllArticlesFromFeed should invoke correct classes`() {
        articleListLocalDataStore.loadAllArticlesFromFeed("test")
        verify(articleDao, times(1)).getAllFromFeed("test")
    }

    @Test
    fun `clear all articles should invoke clear table on Dao`() {
        articleListLocalDataStore.clearAllArticles().blockingAwait()
        verify(articleDao, times(1)).clearTable()
    }
}