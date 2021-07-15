package com.haomins.domain.usecase.article

import com.haomins.domain.TestSchedulers
import com.haomins.domain.repositories.ArticleListRepositoryContract
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.util.concurrent.TimeUnit

class ContinueLoadArticlesByFeedTest {

    @Mock
    lateinit var mockArticleListRepositoryContract: ArticleListRepositoryContract

    private lateinit var continueLoadArticlesByFeed: ContinueLoadArticlesByFeed

    private val executionScheduler = TestSchedulers.executionScheduler()
    private val postExecutionScheduler = TestSchedulers.postExecutionScheduler()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        continueLoadArticlesByFeed = ContinueLoadArticlesByFeed(
            mockArticleListRepositoryContract,
            executionScheduler,
            postExecutionScheduler
        )
    }

    @Test
    fun `test continueLoadArticlesByFeed success`() {

        fun mock() {
            Mockito.`when`(mockArticleListRepositoryContract.continueLoadArticleItemRefs(any())).thenReturn(
                Observable.just(Unit)
            )
        }

        mock()

        val testObserver = TestObserver<Unit>()
        val testExecutionScheduler = executionScheduler.scheduler as TestScheduler
        val testPostExecutionScheduler = postExecutionScheduler.scheduler as TestScheduler

        continueLoadArticlesByFeed
            .buildUseCaseObservable(
                ContinueLoadArticlesByFeed.forContinueLoadArticlesByFeed(
                    "123"
                )
            )
            .subscribeWith(testObserver)

        testObserver.assertSubscribed()

        testExecutionScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        testPostExecutionScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        verify(mockArticleListRepositoryContract, times(1))
            .continueLoadArticleItemRefs("123")

        testObserver.assertComplete()

    }
}