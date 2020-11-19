package com.haomins.www.model.repositories

import com.haomins.www.model.TestSchedulingStrategy
import com.haomins.www.model.data.models.user.UserAuthResponseModel
import com.haomins.www.model.service.TheOldReaderService
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

class LoginRepositoryTest {

    @Mock
    lateinit var mockTheOldReaderService: TheOldReaderService

    private val testScheduler = TestScheduler()
    private val testSchedulingStrategy =
        TestSchedulingStrategy(subscribeOnScheduler = testScheduler)
    private lateinit var loginRepository: LoginRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        loginRepository = LoginRepository(
            theOldReaderService = mockTheOldReaderService,
            defaultSchedulingStrategy = testSchedulingStrategy
        )
        mockHelper()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test start() login call`() {
        val observer = TestObserver<UserAuthResponseModel>()
        loginRepository
            .start(Pair("testId", "testPassword"))
            .subscribe(observer)
        observer.assertSubscribed()
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        observer.assertComplete()
        assertEquals(1, observer.valueCount())
        assertEquals("testSid", observer.values().first().sid)
        assertEquals("testLsid", observer.values().first().lsid)
        assertEquals("testAuth", observer.values().first().auth)
    }

    @Test
    fun `test getSignUpUrlString()`() {
        assertEquals(
            TheOldReaderService.SIGN_UP_PAGE_URL,
            loginRepository.getSignUpUrlString()
        )
    }

    @Test
    fun `test getGenerateAccountUrlString()`() {
        assertEquals(
            TheOldReaderService.GENERATE_ACCOUNT_PAGE_URL,
            loginRepository.getGenerateAccountUrlString()
        )
    }

    private fun mockHelper() {
        `when`(
            mockTheOldReaderService
                .loginUser(
                    userEmail = ArgumentMatchers.anyString(),
                    userPassword = ArgumentMatchers.anyString(),
                    output = ArgumentMatchers.anyString(),
                    service = ArgumentMatchers.anyString(),
                    accountType = ArgumentMatchers.anyString(),
                    client = ArgumentMatchers.anyString()
                )
        )
            .thenReturn(
                Single.timer(100, TimeUnit.MILLISECONDS, testScheduler).flatMap {
                    Single.just(
                        UserAuthResponseModel(
                            sid = "testSid",
                            lsid = "testLsid",
                            auth = "testAuth"
                        )
                    )
                }
            )
    }
}