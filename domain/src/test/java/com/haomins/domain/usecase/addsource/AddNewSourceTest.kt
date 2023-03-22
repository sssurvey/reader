package com.haomins.domain.usecase.addsource

import com.haomins.domain.TestSchedulers
import com.haomins.domain.exception.ParamsShouldNotBeNullException
import com.haomins.domain.repositories.AddSourceRemoteRepository
import com.haomins.domain.usecase.UseCaseConstants.MEDIUM_RSS_FEED_BASE
import com.haomins.model.remote.subscription.AddSourceResponseModel
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.internal.verification.Times
import org.mockito.kotlin.verify
import java.util.concurrent.TimeUnit


class AddNewSourceTest {

    @Mock
    lateinit var mockAddSourceRemoteRepository: AddSourceRemoteRepository

    private val testExecutionScheduler = TestSchedulers.executionScheduler()
    private val testPostExecutionScheduler = TestSchedulers.postExecutionScheduler()

    private lateinit var addNewSource: AddNewSource

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        addNewSource = AddNewSource(
            addSourceRemoteRepository = mockAddSourceRemoteRepository,
            executionScheduler = testExecutionScheduler,
            postExecutionScheduler = testPostExecutionScheduler
        )
    }

    @Test
    fun `test buildUseCaseSingle$domain cannot be null`() {
        val testObserver = TestObserver<AddSourceResponseModel>()
        var isErrorThrown = false

        try {
            addNewSource
                .buildUseCaseSingle(null)
                .subscribe(testObserver)
        } catch (e: IllegalArgumentException) {
            isErrorThrown = e is ParamsShouldNotBeNullException
        }

        testObserver.assertNotSubscribed()
        assertTrue(isErrorThrown)
    }

    @Test
    fun `test buildUseCaseSingle$domain add rss source success`() {

        val testObserver = TestObserver<AddSourceResponseModel>()
        val testSourceString = "123"
        val testAddSourceReturn = Single.just(
            AddSourceResponseModel(
                query = "",
                streamId = "",
                numResults = 1,
                error = "test error"
            )
        )

        `when`(mockAddSourceRemoteRepository.addSource(testSourceString))
            .thenReturn(testAddSourceReturn)

        addNewSource
            .buildUseCaseSingle(AddNewSource.forAddNewRssSource(testSourceString))
            .subscribeWith(testObserver)

        testObserver.assertSubscribed()

        (testPostExecutionScheduler.scheduler as TestScheduler).advanceTimeBy(1, TimeUnit.SECONDS)
        (testPostExecutionScheduler.scheduler as TestScheduler).advanceTimeBy(1, TimeUnit.SECONDS)

        verify(mockAddSourceRemoteRepository, Times(1)).addSource(testSourceString)

        testObserver.assertComplete()
        assertTrue(
            testObserver.values().first() == testAddSourceReturn.blockingGet()
        )
    }


    @Test
    fun `test buildUseCaseSingle$domain add medium source success`() {

        val testObserver = TestObserver<AddSourceResponseModel>()
        val testSourceString = "123"
        val mediumSourceValidator = MEDIUM_RSS_FEED_BASE + testSourceString
        val testAddSourceReturn = Single.just(
            AddSourceResponseModel(
                query = "",
                streamId = "",
                numResults = 1,
                error = "test error"
            )
        )

        `when`(mockAddSourceRemoteRepository.addSource(mediumSourceValidator))
            .thenReturn(testAddSourceReturn)

        addNewSource
            .buildUseCaseSingle(AddNewSource.forAddingNewMediumSource(testSourceString))
            .subscribeWith(testObserver)

        testObserver.assertSubscribed()

        (testPostExecutionScheduler.scheduler as TestScheduler).advanceTimeBy(1, TimeUnit.SECONDS)
        (testPostExecutionScheduler.scheduler as TestScheduler).advanceTimeBy(1, TimeUnit.SECONDS)

        verify(mockAddSourceRemoteRepository, Times(1)).addSource(mediumSourceValidator)

        testObserver.assertComplete()
        assertTrue(
            testObserver.values().first() == testAddSourceReturn.blockingGet()
        )
    }

}