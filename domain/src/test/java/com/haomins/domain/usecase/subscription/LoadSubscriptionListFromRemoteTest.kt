package com.haomins.domain.usecase.subscription

import com.haomins.domain.TestSchedulers
import com.haomins.domain.repositories.remote.SubscriptionRemoteRepository
import com.haomins.model.remote.subscription.SubscriptionItemModel
import com.haomins.model.remote.subscription.SubscriptionListResponseModel
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
import java.util.*
import java.util.concurrent.TimeUnit

class LoadSubscriptionListFromRemoteTest {

    @Mock
    lateinit var mockSubscriptionRemoteRepository: SubscriptionRemoteRepository

    private lateinit var loadSubscriptionListFromRemote: LoadSubscriptionListFromRemote

    private val testExecutionScheduler = TestSchedulers.executionSchedulerTrampolineScheduler()
    private val testPostExecutionScheduler = TestSchedulers.postExecutionSchedulerTrampolineScheduler()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        loadSubscriptionListFromRemote = LoadSubscriptionListFromRemote(
            mockSubscriptionRemoteRepository,
            testExecutionScheduler,
            testPostExecutionScheduler
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test buildUseCaseSingle$domain`() {

        val testSubscriptionItemModels = generateSourceListResponse().subscriptions

        fun mockAction() {
            `when`(mockSubscriptionRemoteRepository.loadSubscriptionList()).thenReturn(
                Single.timer(1, TimeUnit.SECONDS, testExecutionScheduler.scheduler).flatMap {
                    Single.just(
                        testSubscriptionItemModels
                    )
                }
            )
        }

        mockAction()

        val testObserver = loadSubscriptionListFromRemote
            .buildUseCaseSingle(Unit)
            .test()

        testObserver.assertSubscribed()
        testObserver.assertComplete()
        assertTrue(testObserver.values().first() == testSubscriptionItemModels)
    }

    private fun generateSourceListResponse(): SubscriptionListResponseModel {
        val subscriptionsList = mutableListOf<SubscriptionItemModel>()
        for (i in 0 until 100) {
            subscriptionsList
                .add(
                    SubscriptionItemModel(
                        id = "testIdOf::$i",
                        title = "Test Rss Source $i",
                        categories = arrayOf(),
                        sortId = "${i + i}",
                        firstItemMilSec = Calendar.getInstance().time.toString(),
                        url = "https://rss.testdata.com",
                        htmlUrl = "https://rss.testdataABC.com",
                        iconUrl = "https://www.testdataABC.com/image.img"
                    )
                )
        }
        return SubscriptionListResponseModel(
            subscriptions = ArrayList(subscriptionsList)
        )
    }
}