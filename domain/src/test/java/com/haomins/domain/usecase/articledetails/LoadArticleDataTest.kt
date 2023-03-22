package com.haomins.domain.usecase.articledetails

import com.haomins.domain.TestSchedulers
import com.haomins.domain.repositories.ArticleDetailLocalRepository
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
import java.util.concurrent.TimeUnit

class LoadArticleDataTest {

    @Mock
    lateinit var mockArticleDetailLocalRepository: ArticleDetailLocalRepository

    private lateinit var loadArticleData: LoadArticleData
    private val postExecutionScheduler = TestSchedulers.postExecutionScheduler()
    private val executionScheduler = TestSchedulers.executionScheduler()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        loadArticleData = LoadArticleData(
            articleDetailLocalRepository = mockArticleDetailLocalRepository,
            postExecutionScheduler = postExecutionScheduler,
            executionScheduler = executionScheduler
        )
    }

    @Test
    fun `test buildUseCaseSingle$Reader_domain success`() {

        val testObserver = TestObserver<ArticleEntity>()
        val testExecutionScheduler = executionScheduler.scheduler as TestScheduler
        val testId = "test id"
        val testArticleEntity = ArticleEntity(
            itemTitle = "test title",
            itemId = testId,
            author = "test author",
            content = "test content",
            itemPublishedMillisecond = 1,
            itemUpdatedMillisecond = 1,
            href = "www.test.com",
            previewImageUrl = "www.test.com/test.png",
            feedId = "test feed id"
        )

        fun mockBehavior() {
            `when`(mockArticleDetailLocalRepository.loadArticleDetail(testId))
                .thenReturn(
                    Single
                        .timer(1, TimeUnit.SECONDS, testExecutionScheduler)
                        .flatMap {
                            Single.just(testArticleEntity)
                        }
                )
        }

        mockBehavior()

        loadArticleData
            .buildUseCaseSingle(LoadArticleData.forLoadArticleContent(testId))
            .subscribeOn(testExecutionScheduler)
            .subscribeWith(testObserver)

        testExecutionScheduler.advanceTimeBy(2, TimeUnit.SECONDS)
        (postExecutionScheduler.scheduler as TestScheduler).advanceTimeBy(2, TimeUnit.SECONDS)

        testObserver.assertSubscribed()
        testObserver.assertComplete()

        assertTrue(testObserver.values().first() == testArticleEntity)
    }

    @Test
    fun `test buildUseCaseSingle$Reader_domain failed`() {

        val testObserver = TestObserver<ArticleEntity>()
        val testExecutionScheduler = executionScheduler.scheduler as TestScheduler
        val testId = "test id"
        val testException = Exception("test exception")

        fun mockBehavior() {
            `when`(mockArticleDetailLocalRepository.loadArticleDetail(testId))
                .thenReturn(
                    Single
                        .timer(1, TimeUnit.SECONDS, testExecutionScheduler)
                        .flatMap {
                            Single.error(testException)
                        }
                )
        }

        mockBehavior()

        loadArticleData
            .buildUseCaseSingle(LoadArticleData.forLoadArticleContent(testId))
            .subscribeOn(testExecutionScheduler)
            .subscribeWith(testObserver)

        testExecutionScheduler.advanceTimeBy(2, TimeUnit.SECONDS)
        (postExecutionScheduler.scheduler as TestScheduler).advanceTimeBy(2, TimeUnit.SECONDS)

        testObserver.assertSubscribed()
        testObserver.assertError(testException)

    }
}