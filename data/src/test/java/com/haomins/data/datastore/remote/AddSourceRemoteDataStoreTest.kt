package com.haomins.data.datastore.remote

import com.haomins.data.service.TheOldReaderService
import com.haomins.domain.common.PrefUtils
import com.haomins.model.PreferenceKey
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
import org.mockito.kotlin.verifyBlocking
import org.mockito.kotlin.wheneverBlocking

class AddSourceRemoteDataStoreTest {

    @Mock
    lateinit var mockPrefUtils: PrefUtils

    @Mock
    lateinit var mockTheOldReaderService: TheOldReaderService

    private lateinit var addSourceRemoteDataStore: AddSourceRemoteDataStore

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        addSourceRemoteDataStore = AddSourceRemoteDataStore(
            mockTheOldReaderService,
            mockPrefUtils,
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

        wheneverBlocking {
            mockPrefUtils.getString(PreferenceKey.AUTH_CODE_KEY)
        }.thenReturn(testAuthKey)

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

        verifyBlocking(mockPrefUtils) {
            getString(PreferenceKey.AUTH_CODE_KEY)
        }

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