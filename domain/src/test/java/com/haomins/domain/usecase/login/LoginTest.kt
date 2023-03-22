package com.haomins.domain.usecase.login

import com.haomins.domain.TestSchedulers
import com.haomins.domain.repositories.LoginRepositoryContract
import com.haomins.model.remote.user.UserAuthResponseModel
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

class LoginTest {

    @Mock
    lateinit var mockLoginRepositoryContract: LoginRepositoryContract

    private val postExecutionScheduler = TestSchedulers.postExecutionScheduler()
    private val executionScheduler = TestSchedulers.executionScheduler()

    private lateinit var login: Login

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        login = Login(
            loginRepositoryContract = mockLoginRepositoryContract,
            executionScheduler = executionScheduler,
            postExecutionScheduler = postExecutionScheduler
        )
    }

    @Test
    fun `test buildUseCaseSingle$domain success`() {

        val testUserName = "test name"
        val testUserPassword = "test password"
        val testAuth = "test auth"

        fun mockBehavior() {
            `when`(
                mockLoginRepositoryContract.login(
                    testUserName,
                    testUserPassword
                )
            ).thenReturn(
                Single
                    .timer(500, TimeUnit.MILLISECONDS, executionScheduler.scheduler)
                    .flatMap {
                        Single.just(UserAuthResponseModel(auth = testAuth))
                    }
            )
        }

        val testObserver = TestObserver<UserAuthResponseModel>()


        mockBehavior()

        login
            .buildUseCaseSingle(
                Login.forUserLogin(
                    userName = testUserName,
                    userPassword = testUserPassword
                )
            )
            .subscribeWith(testObserver)

        testObserver.assertSubscribed()

        (postExecutionScheduler.scheduler as TestScheduler).advanceTimeBy(
            1000,
            TimeUnit.MILLISECONDS
        )
        (executionScheduler.scheduler as TestScheduler).advanceTimeBy(1000, TimeUnit.MILLISECONDS)

        testObserver.assertComplete()
        assertTrue(testObserver.values().first().auth == testAuth)
    }

    @Test
    fun `test buildUseCaseSingle$domain failure`() {

        val testUserName = "test name"
        val testUserPassword = "test password"
        val testAuth = "test auth"
        val testException = Exception("test exception")

        fun mockBehavior() {
            `when`(
                mockLoginRepositoryContract.login(
                    testUserName,
                    testUserPassword
                )
            ).thenReturn(
                Single
                    .timer(500, TimeUnit.MILLISECONDS, executionScheduler.scheduler)
                    .flatMap {
                        Single.error(testException)
                    }
            )
        }

        val testObserver = TestObserver<UserAuthResponseModel>()


        mockBehavior()

        login
            .buildUseCaseSingle(
                Login.forUserLogin(
                    userName = testUserName,
                    userPassword = testUserPassword
                )
            )
            .subscribeWith(testObserver)

        testObserver.assertSubscribed()

        (postExecutionScheduler.scheduler as TestScheduler).advanceTimeBy(
            1000,
            TimeUnit.MILLISECONDS
        )
        (executionScheduler.scheduler as TestScheduler).advanceTimeBy(1000, TimeUnit.MILLISECONDS)

        testObserver.assertError(testException)
        assertTrue(testObserver.errors().first() == testException)
    }
}