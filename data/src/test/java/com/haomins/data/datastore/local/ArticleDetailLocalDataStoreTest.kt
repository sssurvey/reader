package com.haomins.data.datastore.local

import com.haomins.data.db.dao.ArticleDao
import com.haomins.domain.common.DateUtils
import com.haomins.model.entity.ArticleEntity
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import java.util.concurrent.TimeUnit

class ArticleDetailLocalDataStoreTest {

    @Mock
    lateinit var mockArticleDao: ArticleDao

    @Mock
    lateinit var mockDateUtils: DateUtils

    private val testScheduler = TestScheduler()
    private val testException = Exception("test exception")

    private lateinit var articleDetailLocalDataStore: ArticleDetailLocalDataStore

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        `when`(mockDateUtils.howLongAgo(any())).thenReturn("1")
        `when`(mockDateUtils.to24HrString(any())).thenReturn("1")

        articleDetailLocalDataStore = ArticleDetailLocalDataStore(
            articleDao = mockArticleDao,
        )
    }

    @Test
    fun `test loadArticleDetail success`() {

        val testId = "test id"
        val testArticleEntity = ArticleEntity(
            itemId = testId,
            feedId = "test feed id",
            itemTitle = "test title",
            itemUpdatedMillisecond = 1,
            itemPublishedMillisecond = 1,
            author = "test author",
            content = "test content",
            href = "www.test.com",
            previewImageUrl = "www.test.com/test.jpeg"
        )
        val testObserver = TestObserver<ArticleEntity>()

        fun mockBehavior() {
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

        articleDetailLocalDataStore
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

        articleDetailLocalDataStore
            .loadArticleDetail(testId)
            .subscribeOn(testScheduler)
            .subscribeWith(testObserver)

        testScheduler.advanceTimeBy(2, TimeUnit.SECONDS)

        testObserver.assertSubscribed()
        testObserver.assertError(testException)
    }
}