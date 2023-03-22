package com.haomins.domain.usecase.logging

import com.haomins.domain.TestSchedulers
import com.haomins.domain.repositories.local.LoggingLocalRepository
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.io.File
import java.util.concurrent.TimeUnit

class GetLogFilesTest {

    @Mock
    lateinit var mockLoggingLocalRepository: LoggingLocalRepository

    private val executionScheduler = TestSchedulers.executionScheduler()
    private val postExecutionScheduler = TestSchedulers.postExecutionScheduler()

    private lateinit var getLogFiles: GetLogFiles

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getLogFiles = GetLogFiles(
            mockLoggingLocalRepository,
            executionScheduler,
            postExecutionScheduler
        )
    }

    @Test
    fun `test get log successful`() {

        val tempFile = File.createTempFile("test data", "")

        fun mock() {
            `when`(mockLoggingLocalRepository.getLogFile()).thenReturn(tempFile)
        }

        mock()

        val testObserver = TestObserver<File>()

        getLogFiles.buildUseCaseSingle(Unit).subscribeWith(testObserver)

        testObserver.assertSubscribed()

        (executionScheduler.scheduler as TestScheduler).advanceTimeBy(1, TimeUnit.SECONDS)
        (postExecutionScheduler.scheduler as TestScheduler).advanceTimeBy(1, TimeUnit.SECONDS)

        testObserver.completions()

        assert(testObserver.values().first() == tempFile)

    }
}