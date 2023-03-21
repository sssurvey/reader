package com.haomins.data.repositories

import android.content.SharedPreferences
import com.haomins.data.db.dao.SubscriptionDao
import com.haomins.data.mapper.entitymapper.SubscriptionEntityMapper
import com.haomins.data.model.SharedPreferenceKey
import com.haomins.data.model.responses.subscription.SubscriptionItemModel
import com.haomins.data.service.TheOldReaderService
import com.haomins.domain.model.entities.SubscriptionEntity
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.util.*

class SourceSubscriptionListRepositoryTest {

    @Mock
    lateinit var mockTheOldReaderService: TheOldReaderService

    @Mock
    lateinit var mockSharedPreference: SharedPreferences

    @Mock
    lateinit var mockSubscriptionDao: SubscriptionDao

    private lateinit var sourceSubscriptionListRepository: SourceSubscriptionListRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sourceSubscriptionListRepository = SourceSubscriptionListRepository(
            theOldReaderService = mockTheOldReaderService,
            subscriptionDao = mockSubscriptionDao,
            sharedPreferences = mockSharedPreference,
            subscriptionEntityMapper = SubscriptionEntityMapper()
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test loadSubscriptionList() success`() {

        fun mockHelper() {

            `when`(mockSharedPreference.getString(SharedPreferenceKey.AUTH_CODE_KEY.string, ""))
                .thenReturn("test_key")

            `when`(
                mockTheOldReaderService.loadSubscriptionSourceList(
                    headerAuthValue = TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                            + mockSharedPreference.getString(
                        SharedPreferenceKey.AUTH_CODE_KEY.string,
                        ""
                    )
                )
            ).thenReturn(
                Single.fromCallable(::generateSourceListResponse)
            )

        }

        mockHelper()

        val testObserver = TestObserver<List<SubscriptionEntity>>()
        sourceSubscriptionListRepository
            .loadSubscriptionList()
            .subscribeWith(testObserver)

        testObserver.assertSubscribed()
        testObserver.assertComplete()

        verify(mockSubscriptionDao, times(1)).insertAll(any())
        assertTrue(testObserver.values().first().size == 100)
    }

    @Test
    fun `test loadSubscriptionList() failed`() {

        val testException = Exception()
        val testObserver = TestObserver<List<SubscriptionEntity>>()

        fun mockHelper() {

            `when`(mockSharedPreference.getString(SharedPreferenceKey.AUTH_CODE_KEY.string, ""))
                .thenReturn("test_key")

            `when`(
                mockTheOldReaderService.loadSubscriptionSourceList(
                    headerAuthValue = TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                            + mockSharedPreference.getString(
                        SharedPreferenceKey.AUTH_CODE_KEY.string,
                        ""
                    )
                )
            ).thenReturn(
                Single.error(testException)
            )

            `when`(mockSubscriptionDao.getAll()).thenReturn(
                Single.fromCallable(::generateSubscriptionListEntity)
            )

        }

        mockHelper()

        sourceSubscriptionListRepository
            .loadSubscriptionList()
            .subscribeWith(testObserver)

        testObserver.assertSubscribed()
        testObserver.assertComplete()

        verify(mockSubscriptionDao, times(0)).insertAll(any())
        verify(mockSubscriptionDao, times(1)).getAll()
        assertTrue(testObserver.values().first().size == 100)
    }

    @Test
    fun `test convertSubscriptionItemModelToEntity`() {
        val models = generateSourceListResponse().subscriptions
        val entities =
            sourceSubscriptionListRepository.convertSubscriptionItemModelToEntityForTesting(models)

        assertTrue(models.size == entities.size)

        for (index in 0 until models.size) {
            val entity = entities[index]
            val model = models[index]
            assertTrue(entity.id == model.id)
            assertTrue(entity.firstItemMilSec == model.firstItemMilSec)
            assertTrue(entity.htmlUrl == model.htmlUrl)
            assertTrue(entity.iconUrl == model.iconUrl)
            assertTrue(entity.firstItemMilSec == model.firstItemMilSec)
        }
    }

    private fun generateSubscriptionListEntity(): List<com.haomins.data.model.entities.SubscriptionEntity> {
        val subscriptionEntityList =
            mutableListOf<com.haomins.data.model.entities.SubscriptionEntity>()
        for (i in 0 until 100) {
            subscriptionEntityList.add(
                com.haomins.data.model.entities.SubscriptionEntity(
                    id = i.toString(),
                    title = "test sub entity $i",
                    sortId = (i * i).toString(),
                    firstItemMilSec = "2103123",
                    url = "www.test.com/test$i",
                    htmlUrl = "https://www.test.com/test$i",
                    iconUrl = "https://www.test.com/test$i/image.png"
                )
            )
        }
        return subscriptionEntityList
    }

    private fun generateSourceListResponse(): com.haomins.data.model.responses.subscription.SubscriptionSourceListResponseModel {
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
        return com.haomins.data.model.responses.subscription.SubscriptionSourceListResponseModel(
            subscriptions = ArrayList(subscriptionsList)
        )
    }
}