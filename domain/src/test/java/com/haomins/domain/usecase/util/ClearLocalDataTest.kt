package com.haomins.domain.usecase.util

import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.article.ClearLocalArticles
import com.haomins.domain.usecase.subscription.local.ClearLocalSubscriptions
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class ClearLocalDataTest {

    @Mock
    lateinit var clearLocalArticles: ClearLocalArticles

    @Mock
    lateinit var clearLocalSubscriptions: ClearLocalSubscriptions

    private lateinit var clearLocalData: ClearLocalData

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        clearLocalData = ClearLocalData(
            clearLocalArticles,
            clearLocalSubscriptions,
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
    fun `should initialize correctly`() {
        assertTrue(this::clearLocalData.isInitialized)
    }

    @Test
    fun `clear data should clear all tables`() {
        `when`(clearLocalArticles.buildUseCaseCompletable(Unit)).thenReturn(Completable.complete())
        `when`(clearLocalSubscriptions.buildUseCaseCompletable(Unit)).thenReturn(Completable.complete())
        val testObserver = clearLocalData.buildUseCaseCompletable(Unit).test()
        testObserver.assertComplete()
        verify(clearLocalArticles, times(1)).buildUseCaseCompletable(Unit)
        verify(clearLocalSubscriptions, times(1)).buildUseCaseCompletable(Unit)
    }
}