package com.haomins.data.datastore.remote

import com.haomins.data.MockTheOldReaderService
import com.haomins.domain.common.SharedPrefUtils
import com.haomins.model.SharedPreferenceKey
import com.haomins.model.remote.article.ArticleResponseModel
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.mockito.kotlin.any
import org.mockito.kotlin.verify

class ArticleListRemoteDataStoreTest {

    private lateinit var articleListRemoteDataStore: ArticleListRemoteDataStore

    @Spy
    val mockTheOldReaderService = MockTheOldReaderService()

    @Mock
    lateinit var mockSharedPrefUtils: SharedPrefUtils

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        articleListRemoteDataStore = ArticleListRemoteDataStore(
            theOldReaderService = mockTheOldReaderService,
            sharedPrefUtils = mockSharedPrefUtils,
        )
        mockHelper()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test loadAllArticleItemsFromRemote() should invoke correct classes`() {
        val testObserver =
            TestObserver<Pair<String, List<ArticleResponseModel>>>()
        articleListRemoteDataStore.loadAllArticleItemsFromRemote(
            ""
        ).subscribe(testObserver)
        testObserver.assertSubscribed()
        verify(mockTheOldReaderService, times(1))
            .loadAllArticles(any(), any(), any(), any(), any())
        verify(mockTheOldReaderService, times(10))
            .loadArticleDetailsByRefId(any(), any(), any())
        testObserver.assertValueCount(1)
        testObserver.assertComplete()
        assertTrue(testObserver.values().first().first == "test_continuation")
    }

    @Test
    fun `test loadAllArticleItemsFromRemoteWithFeed() should invoke correct classes`() {
        val testObserver =
            TestObserver<Pair<String, List<ArticleResponseModel>>>()
        articleListRemoteDataStore.loadAllArticleItemsFromRemoteWithFeed(
            "test_feed_id",
            ""
        ).subscribe(testObserver)
        testObserver.assertSubscribed()
        verify(mockTheOldReaderService)
            .loadArticleListByFeed(any(), any(), any(), any(), any())
        verify(mockTheOldReaderService, times(10))
            .loadArticleDetailsByRefId(any(), any(), any())
        assertTrue(testObserver.values().first().first == "test_continuation")
    }

    private fun mockHelper() {
        `when`(
            mockSharedPrefUtils
                .getString(SharedPreferenceKey.AUTH_CODE_KEY)
        )
            .thenReturn("test_auth_code")
    }
}