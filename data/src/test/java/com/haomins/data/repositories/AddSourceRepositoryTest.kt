package com.haomins.data.repositories

import android.content.SharedPreferences
import com.haomins.data.mapper.responsemapper.AddSourceResponseModelMapper
import com.haomins.data_model.SharedPreferenceKey
import com.haomins.data.service.TheOldReaderService
import com.haomins.domain_model.responses.AddSourceResponseModel
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify

class AddSourceRepositoryTest {

    @Mock
    lateinit var mockSharedPreferences: SharedPreferences

    @Mock
    lateinit var mockTheOldReaderService: TheOldReaderService

    private val addSourceResponseModelMapper = AddSourceResponseModelMapper()
    private lateinit var addSourceRepository: AddSourceRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        addSourceRepository = AddSourceRepository(
            addSourceResponseModelMapper,
            mockTheOldReaderService,
            mockSharedPreferences
        )
    }

    @Test
    fun `addSource success call`() {

        val testObserver = TestObserver<com.haomins.domain_model.responses.AddSourceResponseModel>()
        val testSource = "123"
        val testAuthKey = "test_auth_key"
        val testTheOldReaderServiceAddSourceReturn = Single.just(
            com.haomins.data_model.remote.subscription.AddSourceResponseModel(
                query = "test_query",
                numResults = 1,
                streamId = "test_string_id",
                error = "test_error"
            )
        )

        `when`(
            mockSharedPreferences.getString(
                SharedPreferenceKey.AUTH_CODE_KEY.string,
                ""
            )
        ).thenReturn(testAuthKey)

        `when`(
            mockTheOldReaderService.addSubscription(
                headerAuthValue = TheOldReaderService.AUTH_HEADER_VALUE_PREFIX + testAuthKey,
                quickAddSubscription = testSource
            )
        ).thenReturn(
            testTheOldReaderServiceAddSourceReturn
        )

        addSourceRepository
            .addSource(testSource)
            .subscribeWith(testObserver)

        testObserver.assertSubscribed()

        verify(mockSharedPreferences, times(1)).getString(
            SharedPreferenceKey.AUTH_CODE_KEY.string,
            ""
        )

        verify(mockTheOldReaderService, times(1)).addSubscription(
            headerAuthValue = TheOldReaderService.AUTH_HEADER_VALUE_PREFIX + testAuthKey,
            quickAddSubscription = testSource
        )

        testObserver.assertComplete()

        assertTrue(
            testObserver.values()
                .first().result == testTheOldReaderServiceAddSourceReturn.blockingGet().numResults
        )

        assertTrue(
            testObserver.values()
                .first().error == testTheOldReaderServiceAddSourceReturn.blockingGet().error
        )
    }
}