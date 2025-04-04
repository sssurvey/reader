package com.haomins.data.datastore.remote

import com.haomins.data.service.TheOldReaderService
import com.haomins.domain.common.SharedPrefUtils
import com.haomins.model.SharedPreferenceKey
import com.haomins.model.remote.subscription.AddSourceResponseModel
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

class AddSourceRemoteDataStoreTest {

    @Mock
    lateinit var mockSharedPrefUtils: SharedPrefUtils

    @Mock
    lateinit var mockTheOldReaderService: TheOldReaderService

    private lateinit var addSourceRemoteDataStore: AddSourceRemoteDataStore

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        addSourceRemoteDataStore = AddSourceRemoteDataStore(
            mockTheOldReaderService,
            mockSharedPrefUtils,
        )
    }

    @Test
    fun `addSource success call`() {

        val testObserver = TestObserver<AddSourceResponseModel>()
        val testSource = "123"
        val testAuthKey = "test_auth_key"
        val testTheOldReaderServiceAddSourceReturn = Single.just(
            AddSourceResponseModel(
                query = "test_query",
                numResults = 1,
                streamId = "test_string_id",
                error = "test_error"
            )
        )

        `when`(
            mockSharedPrefUtils.getString(SharedPreferenceKey.AUTH_CODE_KEY)
        ).thenReturn(testAuthKey)

        `when`(
            mockTheOldReaderService.addSubscription(
                headerAuthValue = TheOldReaderService.AUTH_HEADER_VALUE_PREFIX + testAuthKey,
                quickAddSubscription = testSource
            )
        ).thenReturn(
            testTheOldReaderServiceAddSourceReturn
        )

        addSourceRemoteDataStore
            .addSource(testSource)
            .subscribeWith(testObserver)

        testObserver.assertSubscribed()

        verify(mockSharedPrefUtils, times(1))
            .getString(SharedPreferenceKey.AUTH_CODE_KEY)

        verify(mockTheOldReaderService, times(1)).addSubscription(
            headerAuthValue = TheOldReaderService.AUTH_HEADER_VALUE_PREFIX + testAuthKey,
            quickAddSubscription = testSource
        )

        testObserver.assertComplete()

        assertTrue(
            testObserver.values()
                .first().numResults == testTheOldReaderServiceAddSourceReturn.blockingGet().numResults
        )

        assertTrue(
            testObserver.values()
                .first().error == testTheOldReaderServiceAddSourceReturn.blockingGet().error
        )
    }
}