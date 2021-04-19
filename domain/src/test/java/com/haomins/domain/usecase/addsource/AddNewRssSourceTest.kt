package com.haomins.domain.usecase.addsource

import com.haomins.domain.TestSchedulers
import com.haomins.domain.exception.ParamsShouldNotBeNullException
import com.haomins.domain.model.AddSourceResponseModel
import com.haomins.domain.repositories.AddSourceRepositoryContract
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.internal.verification.Times
import java.util.concurrent.TimeUnit


class AddNewRssSourceTest {

    @Mock
    lateinit var mockAddSourceRepositoryContract: AddSourceRepositoryContract

    private val testExecutionScheduler = TestSchedulers.executionScheduler()
    private val testPostExecutionScheduler = TestSchedulers.postExecutionScheduler()

    private lateinit var addNewRssSource: AddNewRssSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        addNewRssSource = AddNewRssSource(
            addSourceRepositoryContract = mockAddSourceRepositoryContract,
            executionScheduler = testExecutionScheduler,
            postExecutionScheduler = testPostExecutionScheduler
        )
    }

    @Test
    fun `test buildUseCaseSingle$domain cannot be null`() {
        val testObserver = TestObserver<AddSourceResponseModel>()
        var isErrorThrown = false

        try {
            addNewRssSource
                .buildUseCaseSingle(null)
                .subscribe(testObserver)
        } catch (e: IllegalArgumentException) {
            isErrorThrown = e is ParamsShouldNotBeNullException
        }

        testObserver.assertNotSubscribed()
        assertTrue(isErrorThrown)
    }

    @Test
    fun `test buildUseCaseSingle$domain add source success`() {

        val testObserver = TestObserver<AddSourceResponseModel>()
        val testSourceString = "123"
        val testAddSourceReturn = Single.just(
            AddSourceResponseModel(
                result = 1,
                error = "test error"
            )
        )

        `when`(mockAddSourceRepositoryContract.addSource(testSourceString))
            .thenReturn(testAddSourceReturn)

        addNewRssSource
            .buildUseCaseSingle(AddNewRssSource.forAddNewRssSource(testSourceString))
            .subscribeWith(testObserver)

        testObserver.assertSubscribed()

        (testPostExecutionScheduler.scheduler as TestScheduler).advanceTimeBy(1, TimeUnit.SECONDS)
        (testPostExecutionScheduler.scheduler as TestScheduler).advanceTimeBy(1, TimeUnit.SECONDS)

        verify(mockAddSourceRepositoryContract, Times(1)).addSource(testSourceString)

        testObserver.assertComplete()
        assertTrue(
            testObserver.values().first() == testAddSourceReturn.blockingGet()
        )
    }

}