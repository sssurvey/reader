package com.haomins.www.data.repositories

import com.haomins.domain.model.entities.ArticleEntity
import com.haomins.www.data.db.dao.ArticleDao
import com.haomins.www.data.mapper.ArticleEntityMapper
import com.haomins.www.data.service.RoomService
import com.nhaarman.mockitokotlin2.any
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

class ArticleDetailRepositoryTest {

    @Mock
    lateinit var mockRoomService: RoomService

    @Mock
    lateinit var mockArticleDao: ArticleDao

    private val testScheduler = TestScheduler()
    private val testException = Exception("test exception")

    private lateinit var articleDetailRepository: ArticleDetailRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        articleDetailRepository = ArticleDetailRepository(
                roomService = mockRoomService,
                articleEntityMapper = ArticleEntityMapper()
        )
    }

    @Test
    fun `test loadArticleDetail success`() {

        val testId = "test id"
        val testArticleEntity = com.haomins.www.data.model.entities.ArticleEntity(
                itemId = testId,
                feedId = "test feed id",
                itemTitle = "test title",
                itemUpdatedMillisecond = 1,
                itemPublishedMillisecond = 1,
                author = "test author",
                content = "test content"
        )
        val testObserver = TestObserver<ArticleEntity>()

        fun mockBehavior() {
            `when`(mockRoomService.articleDao())
                    .thenReturn(mockArticleDao)
            `when`(mockArticleDao.selectArticleByItemId(any()))
                    .thenReturn(
                            Single
                                    .timer(1, TimeUnit.SECONDS, testScheduler)
                                    .flatMap {
                                        Single.just(testArticleEntity)
                                    }
                    )
        }

        mockBehavior()

        articleDetailRepository
                .loadArticleDetail(testId)
                .subscribeOn(testScheduler)
                .subscribeWith(testObserver)

        testScheduler.advanceTimeBy(2, TimeUnit.SECONDS)

        testObserver.assertSubscribed()
        testObserver.assertComplete()

        assertTrue(testObserver.values().first().itemId == testId)
    }

    @Test
    fun `test loadArticleDetail fail`() {

        val testId = "test id"
        val testObserver = TestObserver<ArticleEntity>()

        fun mockBehavior() {
            `when`(mockRoomService.articleDao())
                    .thenReturn(mockArticleDao)
            `when`(mockArticleDao.selectArticleByItemId(any()))
                    .thenReturn(
                            Single
                                    .timer(1, TimeUnit.SECONDS, testScheduler)
                                    .flatMap {
                                        Single.error(testException)
                                    }
                    )
        }

        mockBehavior()

        articleDetailRepository
                .loadArticleDetail(testId)
                .subscribeOn(testScheduler)
                .subscribeWith(testObserver)

        testScheduler.advanceTimeBy(2, TimeUnit.SECONDS)

        testObserver.assertSubscribed()
        testObserver.assertError(testException)
    }
}