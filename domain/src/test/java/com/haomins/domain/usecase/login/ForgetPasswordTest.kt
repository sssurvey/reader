package com.haomins.domain.usecase.login

import com.haomins.domain.TestSchedulers
import com.haomins.domain.repositories.LoginRepositoryContract
import io.reactivex.observers.TestObserver
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class ForgetPasswordTest {

    @Mock
    lateinit var mockLoginRepositoryContract: LoginRepositoryContract

    private val postExecutionScheduler = TestSchedulers.postExecutionScheduler()
    private val executionScheduler = TestSchedulers.executionScheduler()

    private lateinit var forgetPassword: ForgetPassword

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        forgetPassword = ForgetPassword(
            loginRepositoryContract = mockLoginRepositoryContract,
            postExecutionScheduler = postExecutionScheduler,
            executionScheduler = executionScheduler
        )
    }

    @Test
    fun `test buildUseCaseSingle$Reader_domain success`() {

        fun mockBehavior() {
            `when`(mockLoginRepositoryContract.getForgetPasswordUrlString()).thenReturn("test")
        }

        val testObserver = TestObserver<String>()

        mockBehavior()

        forgetPassword
            .buildUseCaseSingle(Unit)
            .subscribeWith(testObserver)

        testObserver.assertSubscribed()
        testObserver.assertComplete()
        assertTrue(testObserver.values().first() == "test")
    }
}