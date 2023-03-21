package com.haomins.data.repositories

import android.content.SharedPreferences
import com.haomins.data.MockTheOldReaderService
import com.haomins.data.db.dao.ArticleDao
import com.haomins.data.mapper.entitymapper.ArticleEntityMapper
import com.haomins.data_model.SharedPreferenceKey
import com.haomins.data_model.local.ArticleEntity
import com.haomins.data.service.TheOldReaderService
import com.haomins.data.util.DateUtils
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

class ArticleListRepositoryTest {

    private lateinit var articleListRepository: ArticleListRepository

    @Spy
    val mockTheOldReaderService = MockTheOldReaderService()

    @Mock
    lateinit var mockArticleDao: ArticleDao

    @Mock
    lateinit var mockDateUtils: DateUtils

    @Mock
    lateinit var mockSharedPreferences: SharedPreferences

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        `when`(mockDateUtils.howLongAgo(any())).thenReturn("1")
        `when`(mockDateUtils.to24HrString(any())).thenReturn("1")

        articleListRepository = ArticleListRepository(
            mockTheOldReaderService,
            mockArticleDao,
            mockSharedPreferences,
            ArticleEntityMapper(mockDateUtils)
        )
        mockHelper()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test loadAllArticleItems() order verification`() {
        val testObserver = TestObserver<List<com.haomins.domain.model.entities.ArticleEntity>>()
        articleListRepository.loadAllArticleItems().subscribe(testObserver)
        testObserver.assertSubscribed()
        verify(mockTheOldReaderService, times(1))
            .loadAllArticles(any(), any(), any(), any(), any())
        verify(mockTheOldReaderService, times(10))
            .loadArticleDetailsByRefId(any(), any(), any())
        verify(mockArticleDao, times(1)).insert(any())
        testObserver.assertValueCount(1)
        testObserver.assertComplete()
    }

    @Test
    fun `test loadArticleItemRefs() order verification`() {
        val testObserver = TestObserver<List<com.haomins.domain.model.entities.ArticleEntity>>()
        articleListRepository.loadArticleItems("test_feed_id").subscribe(testObserver)
        testObserver.assertSubscribed()
        verify(mockTheOldReaderService)
            .loadArticleListByFeed(any(), any(), any(), any(), any())
        verify(mockTheOldReaderService, times(10))
            .loadArticleDetailsByRefId(any(), any(), any())
    }

    @Test
    fun `test should load articles from DB directly by feed if error was thrown during api access`() {
        val noConnectionMockTheOldReaderService = mock(TheOldReaderService::class.java)
        val testObserver = TestObserver<List<com.haomins.domain.model.entities.ArticleEntity>>()
        val noConnectionArticleListRepository = ArticleListRepository(
            noConnectionMockTheOldReaderService,
            mockArticleDao,
            mockSharedPreferences,
            ArticleEntityMapper(mockDateUtils)
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
        noConnectionArticleListRepository.loadArticleItems("test_feed_id").subscribe(testObserver)
        testObserver.assertSubscribed()
        verify(mockArticleDao, times(1)).selectAllArticleByFeedId("test_feed_id")
    }

    @Test
    fun `test should load all articles from DB directly if error was thrown during api access`() {
        val noConnectionMockTheOldReaderService = mock(TheOldReaderService::class.java)
        val testObserver = TestObserver<List<com.haomins.domain.model.entities.ArticleEntity>>()
        val noConnectionArticleListRepository = ArticleListRepository(
            noConnectionMockTheOldReaderService,
            mockArticleDao,
            mockSharedPreferences,
            ArticleEntityMapper(mockDateUtils)
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
        noConnectionArticleListRepository.loadAllArticleItems().subscribe(testObserver)
        testObserver.assertSubscribed()
        verify(mockArticleDao, times(1)).getAll()
    }

    @Test
    fun `test continueLoadAllArticleItems()`() {
        val testObserver = TestObserver<List<com.haomins.domain.model.entities.ArticleEntity>>()
        articleListRepository.continueLoadAllArticleItems().subscribe(testObserver)
        testObserver.assertSubscribed()
        verify(mockTheOldReaderService)
            .loadAllArticles(any(), any(), any(), any(), any())
        verify(mockTheOldReaderService, times(10))
            .loadArticleDetailsByRefId(any(), any(), any())
    }

    @Test
    fun continueLoadArticleItemRefs() {
        val testObserver = TestObserver<List<com.haomins.domain.model.entities.ArticleEntity>>()
        articleListRepository.continueLoadArticleItems("test_feed_id").subscribe(testObserver)
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
        `when`(mockArticleDao.getAll())
            .thenReturn(
                Single.just(
                    mutableListOf(
                        ArticleEntity(
                            "1",
                            "2",
                            "test_title",
                            1,
                            1,
                            "test_author",
                            "test_content",
                            "www.test.com",
                            "www.test.com/test.jpeg",
                        ),
                        ArticleEntity(
                            "2",
                            "2",
                            "test_title",
                            1,
                            1,
                            "test_author",
                            "test_content",
                            "www.test.com",
                            "www.test.com/test.jpeg"
                        ),
                        ArticleEntity(
                            "3",
                            "2",
                            "test_title",
                            1,
                            1,
                            "test_author",
                            "test_content",
                            "www.test.com",
                            "www.test.com/test.jpeg"
                        )
                    )
                )
            )
    }
}