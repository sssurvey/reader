package com.haomins.data.repositories

import android.content.SharedPreferences
import com.haomins.data.mapper.responsemapper.UserAuthResponseModelMapper
import com.haomins.data_model.SharedPreferenceKey
import com.haomins.data_model.remote.user.UserAuthResponseModel
import com.haomins.data.service.TheOldReaderService
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
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.util.concurrent.TimeUnit

class LoginRepositoryTest {

    @Mock
    lateinit var mockTheOldReaderService: TheOldReaderService

    @Mock
    lateinit var mockSharedPreferences: SharedPreferences

    @Mock
    lateinit var mockSharedPreferencesEditor: SharedPreferences.Editor

    private lateinit var loginRepository: LoginRepository
    private val testScheduler = TestScheduler()
    private val testException = Exception("test exception")

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        loginRepository = LoginRepository(
            theOldReaderService = mockTheOldReaderService,
            sharedPreferences = mockSharedPreferences,
            userAuthResponseModelMapper = UserAuthResponseModelMapper()
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test start() login call success`() {

        fun mockBehavior() {
            `when`(mockSharedPreferences.edit()).thenReturn(mockSharedPreferencesEditor)
            `when`(
                mockSharedPreferencesEditor.putString(
                    any(),
                    any()
                )
            ).thenReturn(mockSharedPreferencesEditor)
        }

        mockOldReaderServiceBehavior(throwException = false)
        mockBehavior()

        val observer = TestObserver<com.haomins.domain.model.responses.UserAuthResponseModel>()
        loginRepository
            .login("testId", "testPassword")
            .observeOn(testScheduler)
            .subscribe(observer)
        observer.assertSubscribed()
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        observer.assertComplete()
        verify(
            mockSharedPreferencesEditor,
            times(1)
        ).putString(SharedPreferenceKey.AUTH_CODE_KEY.string, "testAuth")
        assertEquals(1, observer.valueCount())
        assertEquals("testAuth", observer.values().first().auth)
    }

    @Test
    fun `test start() login call fail`() {

        fun mockBehavior() {
            `when`(mockSharedPreferences.edit()).thenReturn(mockSharedPreferencesEditor)
            `when`(
                mockSharedPreferencesEditor.remove(
                    any()
                )
            ).thenReturn(mockSharedPreferencesEditor)
        }

        mockOldReaderServiceBehavior(throwException = true)
        mockBehavior()

        val observer = TestObserver<com.haomins.domain.model.responses.UserAuthResponseModel>()
        loginRepository
            .login("testId", "testPassword")
            .observeOn(testScheduler)
            .subscribe(observer)
        observer.assertSubscribed()
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        observer.assertError(testException)
        verify(
            mockSharedPreferencesEditor,
            times(1)
        ).remove(SharedPreferenceKey.AUTH_CODE_KEY.string)
    }

    @Test
    fun `test getSignUpUrlString()`() {
        assertEquals(
            TheOldReaderService.SIGN_UP_PAGE_URL,
            loginRepository.getSignUpUrlString()
        )
    }

    @Test
    fun `test getForgetPasswordUrlString()`() {
        assertEquals(
            TheOldReaderService.FORGET_PASSWORD_PAGE_URL,
            loginRepository.getForgetPasswordUrlString()
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
                    Single.timer(100, TimeUnit.MILLISECONDS, testScheduler).flatMap {
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