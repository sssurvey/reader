package com.haomins.data.datastore.remote

import android.content.SharedPreferences
import com.haomins.data.MockTheOldReaderService
import com.haomins.data.service.TheOldReaderService
import com.haomins.model.SharedPreferenceKey
import com.haomins.model.remote.article.ArticleResponseModel
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.mockito.kotlin.any
import org.mockito.kotlin.verify

class ArticleListRemoteDataStoreTest {

    private lateinit var articleListRemoteDataStore: ArticleListRemoteDataStore

    @Spy
    val mockTheOldReaderService = MockTheOldReaderService()

    @Mock
    lateinit var mockSharedPreferences: SharedPreferences

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        articleListRemoteDataStore = ArticleListRemoteDataStore(
            mockTheOldReaderService,
            mockSharedPreferences,
        )
        mockHelper()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test loadAllArticleItems() order verification`() {
        val testObserver = TestObserver<List<ArticleResponseModel>>()
        articleListRemoteDataStore.loadAllArticleItems().subscribe(testObserver)
        testObserver.assertSubscribed()
        verify(mockTheOldReaderService, times(1))
            .loadAllArticles(any(), any(), any(), any(), any())
        verify(mockTheOldReaderService, times(10))
            .loadArticleDetailsByRefId(any(), any(), any())
        testObserver.assertValueCount(1)
        testObserver.assertComplete()
    }

    @Test
    fun `test loadArticleItemRefs() order verification`() {
        val testObserver = TestObserver<List<ArticleResponseModel>>()
        articleListRemoteDataStore.loadArticleAllItems("test_feed_id").subscribe(testObserver)
        testObserver.assertSubscribed()
        verify(mockTheOldReaderService)
            .loadArticleListByFeed(any(), any(), any(), any(), any())
        verify(mockTheOldReaderService, times(10))
            .loadArticleDetailsByRefId(any(), any(), any())
    }

    @Test
    fun `test should load articles from DB directly by feed if error was thrown during api access`() {
        val noConnectionMockTheOldReaderService = mock(TheOldReaderService::class.java)
        val testObserver = TestObserver<List<ArticleResponseModel>>()
        val noConnectionArticleListRemoteDataStore = ArticleListRemoteDataStore(
            noConnectionMockTheOldReaderService,
            mockSharedPreferences
        )
        `when`(
            noConnectionMockTheOldReaderService.loadArticleListByFeed(
                any(),
                any(),
                any(),
                any(),
                any()
            )
        ).thenReturn(
            Single.error { Exception("test_exception") }
        )
        noConnectionArticleListRemoteDataStore.loadArticleAllItems("test_feed_id")
            .subscribe(testObserver)
        testObserver.assertSubscribed()
    }

    @Test
    fun `test should load all articles from DB directly if error was thrown during api access`() {
        val noConnectionMockTheOldReaderService = mock(TheOldReaderService::class.java)
        val testObserver = TestObserver<List<ArticleResponseModel>>()
        val noConnectionArticleListRemoteDataStore = ArticleListRemoteDataStore(
            noConnectionMockTheOldReaderService,
            mockSharedPreferences
        )
        `when`(
            noConnectionMockTheOldReaderService.loadAllArticles(
                any(),
                any(),
                any(),
                any(),
                any()
            )
        ).thenReturn(
            Single.error { Exception("test_exception") }
        )
        noConnectionArticleListRemoteDataStore.loadAllArticleItems().subscribe(testObserver)
        testObserver.assertSubscribed()
    }

    @Test
    fun `test continueLoadAllArticleItems()`() {
        val testObserver = TestObserver<List<ArticleResponseModel>>()
        articleListRemoteDataStore.continueLoadAllArticleItems().subscribe(testObserver)
        testObserver.assertSubscribed()
        verify(mockTheOldReaderService)
            .loadAllArticles(any(), any(), any(), any(), any())
        verify(mockTheOldReaderService, times(10))
            .loadArticleDetailsByRefId(any(), any(), any())
    }

    @Test
    fun continueLoadArticleItemRefs() {
        val testObserver = TestObserver<List<ArticleResponseModel>>()
        articleListRemoteDataStore.continueLoadAllArticleItems("test_feed_id")
            .subscribe(testObserver)
        testObserver.assertSubscribed()
        verify(mockTheOldReaderService)
            .loadArticleListByFeed(any(), any(), any(), any(), any())
        verify(mockTheOldReaderService, times(10))
            .loadArticleDetailsByRefId(any(), any(), any())
    }

    private fun mockHelper() {
        `when`(
            mockSharedPreferences
                .getString(SharedPreferenceKey.AUTH_CODE_KEY.string, "")
        )
            .thenReturn("test_auth_code")
    }
}