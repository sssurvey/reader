package com.haomins.domain.usecase.subscription

import com.haomins.domain.TestSchedulers
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LoadAndSaveSubscriptionListTest {

    @Mock
    lateinit var loadSubscriptionListFromRemote: LoadSubscriptionListFromRemote

    @Mock
    lateinit var saveSubscriptionListToLocal: SaveSubscriptionListToLocal

    @Mock
    lateinit var loadSubscriptionListFromLocal: LoadSubscriptionListFromLocal

    private lateinit var loadAndSaveSubscriptionList: LoadAndSaveSubscriptionList

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        loadAndSaveSubscriptionList = LoadAndSaveSubscriptionList(
            loadSubscriptionListFromRemote,
            saveSubscriptionListToLocal,
            loadSubscriptionListFromLocal,
            TestSchedulers.executionSchedulerTrampolineScheduler(),
            TestSchedulers.postExecutionScheduler()
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `loadAndSaveSubscriptionList initialized successfully`() {
        assertTrue(this::loadAndSaveSubscriptionList.isInitialized)
    }

    @Test
    fun `are correct instance called when buildUseCaseSuccessful`() {
        
    }
}