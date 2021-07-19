package com.haomins.domain.usecase.disclosure

import com.haomins.domain.TestSchedulers
import com.haomins.domain.model.DisclosureInfo
import com.haomins.domain.repositories.ContactInfoRepositoryContract
import com.haomins.domain.repositories.DisclosureRepositoryContract
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

class LoadDisclosureContentTest {

    @Mock
    lateinit var mockDisclosureRepositoryContract: DisclosureRepositoryContract

    @Mock
    lateinit var mockContractInfoRepositoryContract: ContactInfoRepositoryContract

    private val testExecutionScheduler = TestSchedulers.executionScheduler()
    private val testPostExecutionScheduler = TestSchedulers.postExecutionScheduler()

    private lateinit var loadDisclosureContent: LoadDisclosureContent

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        loadDisclosureContent = LoadDisclosureContent(
            mockDisclosureRepositoryContract,
            mockContractInfoRepositoryContract,
            testExecutionScheduler,
            testPostExecutionScheduler
        )
    }

    @Test
    fun `test loadDisclosureContent successful`() {

        val testObserver = TestObserver<DisclosureInfo>()
        val testDisclosure = "test disclosure"
        val testEmail = "test@test.com"
        val testWebsite = "test.com"

        fun mock() {
            `when`(mockDisclosureRepositoryContract.loadDisclosureContent()).thenReturn(
                Single.just(testDisclosure)
            )
            `when`(mockContractInfoRepositoryContract.getFeedbackEmail()).thenReturn(testEmail)
            `when`(mockContractInfoRepositoryContract.getTheOldReaderSite()).thenReturn(testWebsite)
        }

        mock()

        loadDisclosureContent.buildUseCaseSingle(Unit).subscribeWith(testObserver)

        (testExecutionScheduler.scheduler as TestScheduler).advanceTimeBy(1, TimeUnit.SECONDS)
        (testPostExecutionScheduler.scheduler as TestScheduler).advanceTimeBy(1, TimeUnit.SECONDS)

        testObserver.assertSubscribed()
        testObserver.assertComplete()

        val result = testObserver.values().first()

        assertTrue(result.disclosureContent == testDisclosure)
        assertTrue(result.contactEmail == testEmail)
        assertTrue(result.website == testWebsite)
    }
}