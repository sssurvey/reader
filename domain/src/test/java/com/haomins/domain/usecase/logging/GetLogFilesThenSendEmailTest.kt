package com.haomins.domain.usecase.logging

import com.haomins.domain.TestSchedulers
import com.haomins.domain.repositories.local.LoggingLocalRepository
import com.haomins.domain.usecase.disclosure.LoadDisclosureContent
import com.haomins.model.DisclosureInfo
import com.haomins.model.LogReport
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.io.File
import java.util.concurrent.TimeUnit

class GetLogFilesThenSendEmailTest {

    @Mock
    lateinit var mockLoggingLocalRepository: LoggingLocalRepository

    @Mock
    lateinit var loadDisclosureContent: LoadDisclosureContent

    private val executionScheduler = TestSchedulers.executionScheduler()
    private val postExecutionScheduler = TestSchedulers.postExecutionScheduler()

    private lateinit var getLogFilesThenSendEmail: GetLogFilesThenSendEmail

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getLogFilesThenSendEmail = GetLogFilesThenSendEmail(
            loggingLocalRepository = mockLoggingLocalRepository,
            loadDisclosureContent = loadDisclosureContent,
            executionScheduler = executionScheduler,
            postExecutionScheduler = postExecutionScheduler,
        )
    }

    @Test
    fun `test get log successful`() {

        val tempFile = File.createTempFile("test data", "")
        val testEmail = "test@test.com"

        `when`(mockLoggingLocalRepository.getLogFile()).thenReturn(tempFile)
        `when`(loadDisclosureContent.buildUseCaseSingle(Unit))
            .thenReturn(
                Single.just(
                    DisclosureInfo(
                        disclosureContent = "",
                        contactEmail = testEmail,
                        website = "",
                    )
                )
            )

        val testObserver = TestObserver<LogReport>()

        getLogFilesThenSendEmail.buildUseCaseSingle(Unit)
            .subscribeWith(testObserver)

        testObserver.assertSubscribed()

        (executionScheduler.scheduler as TestScheduler).advanceTimeBy(
            1,
            TimeUnit.SECONDS
        )
        (postExecutionScheduler.scheduler as TestScheduler).advanceTimeBy(
            1,
            TimeUnit.SECONDS
        )

        testObserver.completions()

        assert(testObserver.values().first().file == tempFile)
        assert(testObserver.values().first().email == testEmail)

    }
}