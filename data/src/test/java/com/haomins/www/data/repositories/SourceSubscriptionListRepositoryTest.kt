package com.haomins.www.data.repositories

import android.content.SharedPreferences
import android.widget.ImageView
import com.haomins.www.data.TestSchedulingStrategy
import com.haomins.www.data.db.dao.SubscriptionDao
import com.haomins.www.data.model.SharedPreferenceKey
import com.haomins.www.data.model.entities.SubscriptionEntity
import com.haomins.www.data.model.responses.subscription.SubscriptionItemModel
import com.haomins.www.data.model.responses.subscription.SubscriptionSourceListResponseModel
import com.haomins.www.data.service.GlideService
import com.haomins.www.data.service.RoomService
import com.haomins.www.data.service.TheOldReaderService
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import java.net.URL
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class SourceSubscriptionListRepositoryTest {

    @Mock
    lateinit var mockTheOldReaderService: TheOldReaderService

    @Mock
    lateinit var mockGlideService: GlideService

    @Mock
    lateinit var mockSharedPreference: SharedPreferences

    @Mock
    lateinit var mockSubscriptionDao: SubscriptionDao

    @Mock
    lateinit var mockRoomService: RoomService

    private val testScheduler = TestScheduler()
    private val testSchedulingStrategy =
            TestSchedulingStrategy(subscribeOnScheduler = testScheduler)
    private lateinit var sourceSubscriptionListRepository: SourceSubscriptionListRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sourceSubscriptionListRepository = SourceSubscriptionListRepository(
                theOldReaderService = mockTheOldReaderService,
                roomService = mockRoomService,
                glideService = mockGlideService,
                sharedPreferences = mockSharedPreference,
                defaultSchedulingStrategy = testSchedulingStrategy
        )
        mockHelper()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test loadSubList()`() {
        val observer = TestObserver<SubscriptionSourceListResponseModel>()
        sourceSubscriptionListRepository.loadSubList().subscribe(observer)
        observer.assertSubscribed()
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        observer.assertComplete()
    }

    @Test
    fun `test loadIconImage() success`() {
        val imageView = Mockito.mock(ImageView::class.java)
        val url = Mockito.mock(URL::class.java)
        sourceSubscriptionListRepository.loadIconImage(
                imageView,
                url
        )
        verify(mockGlideService).loadIconImage(
                imageView,
                url
        )
    }

    @Test
    fun `test retrieveSubListFromDB() success`() {
        val observer = TestObserver<List<SubscriptionEntity>>()
        sourceSubscriptionListRepository.retrieveSubListFromDB().subscribe(observer)
        observer.assertSubscribed()
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        observer.assertComplete()
        assertEquals(100, observer.values().first().size)
        assertEquals("2103123", observer.values().first().first().firstItemMilSec)
    }

    @Test
    fun `test saveSubListToDB() success with clear table == true`() {
        val observer = TestObserver<Unit>()
        sourceSubscriptionListRepository.saveSubListToDB(
                subscriptionSourceListResponseModel = generateSourceListResponse(),
                clearOldTable = true
        ).subscribe(observer)
        observer.assertSubscribed()
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        verify(mockSubscriptionDao).clearTable()
        verify(mockSubscriptionDao).insertAll(com.nhaarman.mockitokotlin2.any())
        observer.assertComplete()
    }

    @Test
    fun `test saveSubListToDB() success with clear table == false`() {
        val observer = TestObserver<Unit>()
        sourceSubscriptionListRepository.saveSubListToDB(
                subscriptionSourceListResponseModel = generateSourceListResponse(),
                clearOldTable = false
        ).subscribe(observer)
        observer.assertSubscribed()
        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
        verify(mockSubscriptionDao, times(0)).clearTable()
        verify(mockSubscriptionDao).insertAll(com.nhaarman.mockitokotlin2.any())
        observer.assertComplete()
    }

    private fun mockHelper() {

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
                Single.timer(100, TimeUnit.MILLISECONDS, testScheduler).flatMap {
                    Single.fromCallable(::generateSourceListResponse)
                }
        )

        `when`(mockRoomService.subscriptionDao()).thenReturn(mockSubscriptionDao)

        `when`(mockSubscriptionDao.getAll()).thenReturn(
                Single.timer(100, TimeUnit.MILLISECONDS, testScheduler).flatMap {
                    Single.fromCallable(::generateSubscriptionListEntity)
                }
        )

    }

    private fun generateSubscriptionListEntity(): List<SubscriptionEntity> {
        val subscriptionEntityList = mutableListOf<SubscriptionEntity>()
        for (i in 0 until 100) {
            subscriptionEntityList.add(
                    SubscriptionEntity(
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

    private fun generateSourceListResponse(): SubscriptionSourceListResponseModel {
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
        return SubscriptionSourceListResponseModel(
                subscriptions = ArrayList(subscriptionsList)
        )
    }
}