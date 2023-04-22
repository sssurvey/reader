package com.haomins.domain.usecase.util

import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.inOrder
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

internal class ClearAndGetLocalDataSizeTest {

    @Mock
    lateinit var clearLocalData: ClearLocalData

    @Mock
    lateinit var getLocalDataSize: GetLocalDataSize

    private lateinit var clearAndGetLocalDataSize: ClearAndGetLocalDataSize

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        clearAndGetLocalDataSize = ClearAndGetLocalDataSize(
            clearLocalData, getLocalDataSize,
            object : ExecutionScheduler {
                override val scheduler: Scheduler
                    get() = Schedulers.trampoline()
            },
            object : PostExecutionScheduler {
                override val scheduler: Scheduler
                    get() = Schedulers.trampoline()
            }
        )
    }

    @Test
    fun `is initialized successfully`() {
        assertTrue(this::clearAndGetLocalDataSize.isInitialized)
    }

    @Test
    fun `build use case should clear local data first, then get local data size`() {
        `when`(clearLocalData.buildUseCaseCompletable(Unit))
            .thenReturn(Completable.complete())
        `when`(getLocalDataSize.buildUseCaseSingle(Unit))
            .thenReturn(Single.just(1L))
        val testObserver = clearAndGetLocalDataSize.buildUseCaseSingle(Unit).test()
        testObserver.assertComplete()
        verify(clearLocalData, times(1)).buildUseCaseCompletable(Unit)
        verify(getLocalDataSize, times(1)).buildUseCaseSingle(Unit)
        val inOrder = inOrder(clearLocalData, getLocalDataSize)
        inOrder.verify(clearLocalData).buildUseCaseCompletable(Unit)
        inOrder.verify(getLocalDataSize).buildUseCaseSingle(Unit)
    }
}