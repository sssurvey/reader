package com.haomins.domain.usecase.source

import com.haomins.domain.TestSchedulers
import com.haomins.domain.model.entities.SubscriptionEntity
import com.haomins.domain.repositories.SourceSubscriptionListRepositoryContract
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

class LoadSubscriptionListTest {

    @Mock
    lateinit var mockSourceSubscriptionListRepositoryContract: SourceSubscriptionListRepositoryContract

    private lateinit var loadSubscriptionList: LoadSubscriptionList

    private val testExecutionScheduler = TestSchedulers.executionScheduler()
    private val testPostExecutionScheduler = TestSchedulers.postExecutionScheduler()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        loadSubscriptionList = LoadSubscriptionList(
            mockSourceSubscriptionListRepositoryContract,
            testExecutionScheduler,
            testPostExecutionScheduler
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test buildUseCaseSingle$domain`() {

        val testObserver = TestObserver<List<SubscriptionEntity>>()
        val testEntity = SubscriptionEntity(
            title = "test",
            iconUrl = "test_url",
            id = "123"
        )

        fun mockAction() {
            `when`(mockSourceSubscriptionListRepositoryContract.loadSubscriptionList()).thenReturn(
                Single.timer(1, TimeUnit.SECONDS, testExecutionScheduler.scheduler).flatMap {
                    Single.just(
                        listOf(
                            testEntity
                        )
                    )
                }
            )
        }

        mockAction()

        loadSubscriptionList
            .buildUseCaseSingle(Unit)
            .subscribeWith(testObserver)

        testObserver.assertSubscribed()

        (testExecutionScheduler.scheduler as TestScheduler).advanceTimeBy(10, TimeUnit.SECONDS)
        (testPostExecutionScheduler.scheduler as TestScheduler).advanceTimeBy(11, TimeUnit.SECONDS)

        testObserver.assertComplete()
        assertTrue(testObserver.values().first().first() == testEntity)
    }
}