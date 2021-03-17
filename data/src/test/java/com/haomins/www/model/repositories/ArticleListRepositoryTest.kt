package com.haomins.www.model.repositories

import android.content.SharedPreferences
import com.haomins.www.model.MockTheOldReaderService
import com.haomins.www.model.TestSchedulingStrategy
import com.haomins.www.model.model.SharedPreferenceKey
import com.haomins.www.model.model.entities.ArticleEntity
import com.haomins.www.model.db.dao.ArticleDao
import com.haomins.www.model.service.RoomService
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import java.util.concurrent.TimeUnit

class ArticleListRepositoryTest {

    private val testScheduler = TestScheduler()
    private val testSchedulingStrategy =
        TestSchedulingStrategy(subscribeOnScheduler = testScheduler)
    private lateinit var articleListRepository: ArticleListRepository

    @Spy
    val mockTheOldReaderService = MockTheOldReaderService(testSchedulingStrategy)

    @Mock
    lateinit var mockRoomService: RoomService

    @Mock
    lateinit var mockArticleDao: ArticleDao

    @Mock
    lateinit var mockSharedPreferences: SharedPreferences

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        articleListRepository = ArticleListRepository(
            mockTheOldReaderService,
            mockRoomService,
            mockSharedPreferences,
            testSchedulingStrategy
        )
        mockHelper()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test loadAllArticleItemRefs() order verification`() {
        val testObserver = TestObserver<List<ArticleEntity>>()
        articleListRepository.loadAllArticleItemRefs().subscribe(testObserver)
        testObserver.assertSubscribed()
        testScheduler.advanceTimeBy(2000, TimeUnit.MILLISECONDS)
        verify(mockRoomService, times(20)).articleDao()
        verify(mockArticleDao, times(10)).insert(any())
        testObserver.assertValueCount(1)
        testObserver.assertComplete()
    }

    @Test
    fun `test loadArticleItemRefs() order verification`() {
        val testObserver = TestObserver<List<ArticleEntity>>()
        articleListRepository.loadArticleItemRefs("test_feed_id").subscribe(testObserver)
        testObserver.assertSubscribed()
        verify(mockTheOldReaderService)
            .loadArticleListByFeed(any(), any(), any(), any(), any())
        testScheduler.advanceTimeBy(2000, TimeUnit.MILLISECONDS)
        verify(mockTheOldReaderService, times(10))
            .loadArticleDetailsByRefId(any(), any(), any())
        testScheduler.advanceTimeBy(2000, TimeUnit.MILLISECONDS)
        verify(mockRoomService, times(2)).articleDao()
    }

    @Test
    fun continueLoadAllArticleItemRefs() {
        val testObserver = TestObserver<Unit>()
        articleListRepository.continueLoadAllArticleItemRefs().subscribe(testObserver)
        testObserver.assertSubscribed()
        testScheduler.advanceTimeBy(2000, TimeUnit.MILLISECONDS)
        verify(mockTheOldReaderService)
            .loadAllArticles(any(), any(), any(), any(), any())
        testScheduler.advanceTimeBy(2000, TimeUnit.MILLISECONDS)
        verify(mockTheOldReaderService, times(10))
            .loadArticleDetailsByRefId(any(), any(), any())
        testScheduler.advanceTimeBy(2000, TimeUnit.MILLISECONDS)
        verify(mockRoomService, times(10)).articleDao()
    }

    @Test
    fun continueLoadArticleItemRefs() {
        val testObserver = TestObserver<Unit>()
        articleListRepository.continueLoadArticleItemRefs("test_feed_id").subscribe(testObserver)
        testObserver.assertSubscribed()
        testScheduler.advanceTimeBy(2000, TimeUnit.MILLISECONDS)
        verify(mockTheOldReaderService)
            .loadArticleListByFeed(any(), any(), any(), any(), any())
        testScheduler.advanceTimeBy(2000, TimeUnit.MILLISECONDS)
        verify(mockTheOldReaderService, times(10))
            .loadArticleDetailsByRefId(any(), any(), any())
        testScheduler.advanceTimeBy(2000, TimeUnit.MILLISECONDS)
        verify(mockRoomService, times(10)).articleDao()
    }

    private fun mockHelper() {
        `when`(
            mockSharedPreferences
                .getString(SharedPreferenceKey.AUTH_CODE_KEY.string, "")
        )
            .thenReturn("test_auth_code")
        `when`(mockRoomService.articleDao())
            .thenReturn(mockArticleDao)
        `when`(mockArticleDao.getAll())
            .thenReturn(
                Observable.just(
                    mutableListOf(
                        ArticleEntity(
                            "1",
                            "2",
                            "test_title",
                            1,
                            1,
                            "test_author",
                            "test_content"
                        ),
                        ArticleEntity(
                            "2",
                            "2",
                            "test_title",
                            1,
                            1,
                            "test_author",
                            "test_content"
                        ),
                        ArticleEntity(
                            "3",
                            "2",
                            "test_title",
                            1,
                            1,
                            "test_author",
                            "test_content"
                        )
                    )
                )
            )
    }
}