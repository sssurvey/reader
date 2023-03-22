package com.haomins.domain.usecase.subscription

import com.haomins.domain.MockGenerator
import com.haomins.domain.TestSchedulers
import com.haomins.domain.repositories.local.SubscriptionLocalRepository
import com.haomins.domain.usecase.subscription.local.LoadSubscriptionListFromLocal
import io.reactivex.Single
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class LoadSubscriptionListFromLocalTest {

    @Mock
    lateinit var subscriptionLocalRepository: SubscriptionLocalRepository

    private lateinit var loadSubscriptionListFromLocal: LoadSubscriptionListFromLocal

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        loadSubscriptionListFromLocal = LoadSubscriptionListFromLocal(
            subscriptionLocalRepository,
            TestSchedulers.executionSchedulerTrampolineScheduler(),
            TestSchedulers.postExecutionSchedulerTrampolineScheduler()
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `is loadSubscriptionListFromLocal initialized successfully`() {
        assertTrue(this::loadSubscriptionListFromLocal.isInitialized)
    }

    @Test
    fun `LoadAndSaveSubscriptionList buildUseCaseSingle should invoke correct classes`() {
        `when`(subscriptionLocalRepository.loadAllSubscriptionFromLocal())
            .then {
                Single.fromCallable { MockGenerator.generateSubscriptionEntity() }
            }
        val testObserver = loadSubscriptionListFromLocal.buildUseCaseSingle(Unit).test()
        testObserver.assertComplete()
        verify(subscriptionLocalRepository, times(1)).loadAllSubscriptionFromLocal()
    }
}