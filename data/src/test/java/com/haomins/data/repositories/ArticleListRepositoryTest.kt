package com.haomins.data.repositories

import android.content.SharedPreferences
import com.haomins.data.MockTheOldReaderService
import com.haomins.data.TestSchedulingStrategy
import com.haomins.data.db.dao.ArticleDao
import com.haomins.data.mapper.entitymapper.ArticleEntityMapper
import com.haomins.data.model.SharedPreferenceKey
import com.haomins.data.model.entities.ArticleEntity
import com.haomins.data.service.RoomService
import com.haomins.data.util.DateUtils
import io.reactivex.Single
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
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
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
                mockRoomService,
                mockSharedPreferences,
                ArticleEntityMapper(mockDateUtils)
        )
        mockHelper()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test loadAllArticleItemRefs() order verification`() {
        val testObserver = TestObserver<List<com.haomins.domain.model.entities.ArticleEntity>>()
        articleListRepository.loadAllArticleItemRefs().subscribeWith(testObserver)
        testObserver.assertSubscribed()
        testScheduler.advanceTimeBy(2000, TimeUnit.MILLISECONDS)
        verify(mockTheOldReaderService, times(1))
            .loadAllArticles(any(), any(), any(), any(), any())
        verify(mockRoomService, times(20)).articleDao()
        verify(mockArticleDao, times(10)).insert(any())
        testObserver.assertValueCount(10)
        testObserver.assertComplete()
    }

    @Test
    fun `test loadArticleItemRefs() order verification`() {
        val testObserver = TestObserver<List<com.haomins.domain.model.entities.ArticleEntity>>()
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
                        Single.just(
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