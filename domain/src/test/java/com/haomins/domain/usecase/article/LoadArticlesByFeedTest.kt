package com.haomins.domain.usecase.article

import com.haomins.domain.TestSchedulers
import com.haomins.domain.model.entities.ArticleEntity
import com.haomins.domain.repositories.ArticleListRepositoryContract
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.util.concurrent.TimeUnit

class LoadArticlesByFeedTest {

    @Mock
    lateinit var mockArticleListRepositoryContract: ArticleListRepositoryContract

    private lateinit var loadAllArticlesByFeed: LoadArticlesByFeed

    private val executionScheduler = TestSchedulers.executionScheduler()
    private val postExecutionScheduler = TestSchedulers.postExecutionScheduler()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        loadAllArticlesByFeed = LoadArticlesByFeed(
            mockArticleListRepositoryContract,
            executionScheduler,
            postExecutionScheduler
        )
    }

    @Test
    fun `test loadAllArticlesByFeed success`() {

        val testArticleEntityList = listOf(
            ArticleEntity(
                "123",
                "123",
                "123",
                "123",
                "123",
                "123",
                123,
                123,
                "",
                ""
            ),
            ArticleEntity(
                "123",
                "1234",
                "123",
                "123",
                "123",
                "123",
                123,
                123,
                "",
                ""
            )
        )

        fun mock() {
            Mockito.`when`(mockArticleListRepositoryContract.loadArticleItems("123"))
                .thenReturn(
                    Single.just(
                        testArticleEntityList
                    )
                )
        }

        mock()

        val testObserver = TestObserver<List<ArticleEntity>>()
        val testExecutionScheduler = executionScheduler.scheduler as TestScheduler
        val testPostExecutionScheduler = postExecutionScheduler.scheduler as TestScheduler

        loadAllArticlesByFeed
            .buildUseCaseSingle(LoadArticlesByFeed.forLoadArticlesByFeed("123"))
            .subscribe(testObserver)

        testObserver.assertSubscribed()

        testExecutionScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        testPostExecutionScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        verify(mockArticleListRepositoryContract, times(1))
            .loadArticleItems("123")

        Assert.assertTrue((testObserver.events[0][0] as List<ArticleEntity>)[0].itemId == "123")

        testObserver.assertComplete()
    }
}