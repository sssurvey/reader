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

class SignUpTest {

    @Mock
    lateinit var mockLoginRepositoryContract: LoginRepositoryContract

    private val postExecutionScheduler = TestSchedulers.postExecutionScheduler()
    private val executionScheduler = TestSchedulers.executionScheduler()

    private lateinit var signUp: SignUp

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        signUp = SignUp(
            loginRepositoryContract = mockLoginRepositoryContract,
            postExecutionScheduler,
            executionScheduler
        )
    }

    @Test
    fun `test buildUseCaseSingle$Reader_domain success`() {

        fun mockBehavior() {
            `when`(mockLoginRepositoryContract.getSignUpUrlString()).thenReturn("test string")
        }

        val testObserver = TestObserver<String>()

        mockBehavior()

        signUp.buildUseCaseSingle(Unit).subscribeWith(testObserver)

        testObserver.assertSubscribed()
        testObserver.assertComplete()

        assertTrue(testObserver.values().first() == "test string")
    }
}