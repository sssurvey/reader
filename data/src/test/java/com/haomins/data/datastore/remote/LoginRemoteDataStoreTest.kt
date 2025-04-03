package com.haomins.data.datastore.remote

import com.haomins.data.service.TheOldReaderService
import com.haomins.domain.common.SharedPrefUtils
import com.haomins.model.SharedPreferenceKey
import com.haomins.model.remote.user.UserAuthResponseModel
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
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.util.concurrent.TimeUnit

class LoginRemoteDataStoreTest {

    @Mock
    lateinit var mockTheOldReaderService: TheOldReaderService

    @Mock
    lateinit var mockSharedPrefUtils: SharedPrefUtils

    private lateinit var loginRemoteDataStore: LoginRemoteDataStore
    private val testScheduler = TestScheduler()
    private val testException = Exception("test exception")

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        loginRemoteDataStore = LoginRemoteDataStore(
            theOldReaderService = mockTheOldReaderService,
            sharedPrefUtils = mockSharedPrefUtils,
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test start() login call success`() {

        mockOldReaderServiceBehavior(throwException = false)

        val observer = TestObserver<UserAuthResponseModel>()
        loginRemoteDataStore
            .login("testId", "testPassword")
            .observeOn(testScheduler)
            .subscribe(observer)
        observer.assertSubscribed()
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        observer.assertComplete()
        verify(
            mockSharedPrefUtils,
            times(1)
        ).putValue(SharedPreferenceKey.AUTH_CODE_KEY, "testAuth")
        assertEquals(1, observer.valueCount())
        assertEquals("testAuth", observer.values().first().auth)
    }

    @Test
    fun `test start() login call fail`() {

        mockOldReaderServiceBehavior(throwException = true)

        val observer = TestObserver<UserAuthResponseModel>()
        loginRemoteDataStore
            .login("testId", "testPassword")
            .observeOn(testScheduler)
            .subscribe(observer)
        observer.assertSubscribed()
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        observer.assertError(testException)
        verify(
            mockSharedPrefUtils,
            times(1)
        ).removeValue(SharedPreferenceKey.AUTH_CODE_KEY)
    }

    @Test
    fun `test getSignUpUrlString()`() {
        assertEquals(
            TheOldReaderService.SIGN_UP_PAGE_URL,
            loginRemoteDataStore.getSignUpUrlString()
        )
    }

    @Test
    fun `test getForgetPasswordUrlString()`() {
        assertEquals(
            TheOldReaderService.FORGET_PASSWORD_PAGE_URL,
            loginRemoteDataStore.getForgetPasswordUrlString()
        )
    }

    private fun mockOldReaderServiceBehavior(throwException: Boolean) {
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
                if (!throwException) {
                    Single.timer(100, TimeUnit.MILLISECONDS, testScheduler)
                        .flatMap {
                            Single.just(
                                UserAuthResponseModel(
                                    sid = "testSid",
                                    lsid = "testLsid",
                                    auth = "testAuth"
                                )
                            )
                        }
                } else {
                    Single.error(testException)
                }
            )
    }
}