package com.haomins.www.core.repositories

import android.content.SharedPreferences
import com.haomins.www.core.MockTheOldReaderService
import com.haomins.www.core.TestSchedulingStrategy
import com.haomins.www.core.data.SharedPreferenceKey
import com.haomins.www.core.data.entities.ArticleEntity
import com.haomins.www.core.db.dao.ArticleDao
import com.haomins.www.core.service.RoomService
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
import java.util.concurrent.TimeUnit

class ArticleListRepositoryTest {

    @Mock
    lateinit var mockRoomService: RoomService

    @Mock
    lateinit var mockArticleDao: ArticleDao

    @Mock
    lateinit var mockSharedPreferences: SharedPreferences

    private val testScheduler = TestScheduler()
    private val testSchedulingStrategy = TestSchedulingStrategy(subscribeOnScheduler = testScheduler)
    private val mockTheOldReaderService = MockTheOldReaderService(testSchedulingStrategy)
    private lateinit var articleListRepository: ArticleListRepository

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
    fun loadAllArticleItemRefs() {
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
    fun loadArticleItemRefs() {
        val testObserver = TestObserver<List<ArticleEntity>>()
        articleListRepository.loadArticleItemRefs("test_feed_id").subscribe(testObserver)
        testObserver.assertSubscribed()
    }

    @Test
    fun continueLoadAllArticleItemRefs() {
    }

    @Test
    fun continueLoadArticleItemRefs() {
    }

    private fun mockHelper() {
        `when`(mockSharedPreferences
            .getString(SharedPreferenceKey.AUTH_CODE_KEY.string, ""))
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