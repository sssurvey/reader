package com.haomins.data.datastore.remote

import com.haomins.data.service.TheOldReaderService
import com.haomins.domain.common.PrefUtils
import com.haomins.model.PreferenceKey
import com.haomins.model.remote.subscription.SubscriptionItemModel
import io.reactivex.Single
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.wheneverBlocking
import java.util.Calendar

class SubscriptionRemoteDataStoreTest {

    @Mock
    lateinit var mockTheOldReaderService: TheOldReaderService

    @Mock
    lateinit var mockPrefUtils: PrefUtils

    private lateinit var subscriptionRemoteDataStore: SubscriptionRemoteDataStore

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        subscriptionRemoteDataStore = SubscriptionRemoteDataStore(
            theOldReaderService = mockTheOldReaderService,
            prefUtils = mockPrefUtils
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test loadSubscriptionList() success`() {
        wheneverBlocking {
            mockTheOldReaderService.loadSubscriptionSourceList(
                headerAuthValue = TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                        + mockPrefUtils
                    .getString(PreferenceKey.AUTH_CODE_KEY)
            )
        }.thenReturn(
            Single.fromCallable(::generateSourceListResponse)
        )

        val testObserver = subscriptionRemoteDataStore
            .loadSubscriptionList()
            .test()

        testObserver.assertSubscribed()
        testObserver.assertComplete()

        assertTrue(testObserver.values().first().size == 100)
    }

    @Test
    fun `test loadSubscriptionList() failed`() {

        val testException = Exception()

        wheneverBlocking {
            mockTheOldReaderService.loadSubscriptionSourceList(
                headerAuthValue = TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                        + mockPrefUtils
                    .getString(PreferenceKey.AUTH_CODE_KEY)
            )
        }.thenReturn(
            Single.error(testException)
        )

        val testObserver = subscriptionRemoteDataStore
            .loadSubscriptionList()
            .test()

        testObserver.assertSubscribed()
        testObserver.assertError(testException)
    }

    private fun generateSourceListResponse(): com.haomins.model.remote.subscription.SubscriptionListResponseModel {
        val subscriptionsList = mutableListOf<SubscriptionItemModel>()
        for (i in 0 until 100) {
            subscriptionsList
                .add(
                    SubscriptionItemModel(
                        id = "testIdOf::$i",
                        title = "Test Rss Source $i",
                        categories = arrayOf(),
                        sortId = "${i + i}",
                        firstItemMilSec = Calendar.getInstance().time.toString(),
                        url = "https://rss.testdata.com",
                        htmlUrl = "https://rss.testdataABC.com",
                        iconUrl = "https://www.testdataABC.com/image.img"
                    )
                )
        }
        return com.haomins.model.remote.subscription.SubscriptionListResponseModel(
            subscriptions = ArrayList(subscriptionsList)
        )
    }
}