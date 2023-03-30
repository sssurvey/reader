package com.haomins.domain.usecase.subscription.local

import com.haomins.domain.MockGenerator
import com.haomins.domain.TestSchedulers
import com.haomins.domain.repositories.local.SubscriptionLocalRepository
import io.reactivex.Completable
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class SaveSubscriptionListToLocalTest {

    @Mock
    lateinit var subscriptionLocalRepository: SubscriptionLocalRepository

    private lateinit var saveSubscriptionListToLocal: SaveSubscriptionListToLocal

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        saveSubscriptionListToLocal = SaveSubscriptionListToLocal(
            subscriptionLocalRepository,
            TestSchedulers.executionSchedulerTrampolineScheduler(),
            TestSchedulers.postExecutionSchedulerTrampolineScheduler()
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `saveSubscriptionListToLocal should initialize successfully`() {
        assertTrue(this::saveSubscriptionListToLocal.isInitialized)
    }

    @Test
    fun `saveSubscriptionListToLocal buildUseCaseSingle should invoke correct classes`() {
        val testSubscriptionEntities = MockGenerator.generateSubscriptionEntity()

        `when`(
            subscriptionLocalRepository.saveAllSubscriptionToLocal(testSubscriptionEntities)
        ).then {
            Completable.complete()
        }

        val testObserver = saveSubscriptionListToLocal.buildUseCaseCompletable(
            SaveSubscriptionListToLocal
                .forSaveSubscriptionList(
                    testSubscriptionEntities
                )
        ).test()

        testObserver.assertComplete()

        verify(subscriptionLocalRepository, times(1)).saveAllSubscriptionToLocal(
            testSubscriptionEntities
        )
    }
}