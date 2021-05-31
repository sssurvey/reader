package com.haomins.data.repositories

import android.content.SharedPreferences
import com.haomins.data.db.dao.SubscriptionDao
import com.haomins.data.mapper.entitymapper.SubscriptionEntityMapper
import com.haomins.data.model.SharedPreferenceKey
import com.haomins.data.model.responses.subscription.SubscriptionItemModel
import com.haomins.data.service.RoomService
import com.haomins.data.service.TheOldReaderService
import com.haomins.domain.model.entities.SubscriptionEntity
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.*
import kotlin.collections.ArrayList

class SourceSubscriptionListRepositoryTest {

    @Mock
    lateinit var mockTheOldReaderService: TheOldReaderService

    @Mock
    lateinit var mockSharedPreference: SharedPreferences

    @Mock
    lateinit var mockSubscriptionDao: SubscriptionDao

    @Mock
    lateinit var mockRoomService: RoomService

    private lateinit var sourceSubscriptionListRepository: SourceSubscriptionListRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        sourceSubscriptionListRepository = SourceSubscriptionListRepository(
            theOldReaderService = mockTheOldReaderService,
            roomService = mockRoomService,
            sharedPreferences = mockSharedPreference,
            subscriptionEntityMapper = SubscriptionEntityMapper()
        )
        mockHelper()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test loadSubscriptionList()`() {

        val testObserver = TestObserver<List<SubscriptionEntity>>()



//        sourceSubscriptionListRepository
//            .loadSubscriptionList()
//            .subscribeWith(testObserver)
//
//        testObserver.assertComplete()

//        sourceSubscriptionListRepository.loadSubList().subscribe(observer)
//        observer.assertSubscribed()
//        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
//        observer.assertComplete()
    }

//    @Test
//    fun `test loadIconImage() success`() {
//        val imageView = Mockito.mock(ImageView::class.java)
//        val url = Mockito.mock(URL::class.java)
//        sourceSubscriptionListRepository.loadIconImage(
//                imageView,
//                url
//        )
//        verify(mockGlideService).loadIconImage(
//                imageView,
//                url
//        )
//    }
//
//    @Test
//    fun `test retrieveSubListFromDB() success`() {
//        val observer = TestObserver<List<SubscriptionEntity>>()
//        sourceSubscriptionListRepository.retrieveSubListFromDB().subscribe(observer)
//        observer.assertSubscribed()
//        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
//        observer.assertComplete()
//        assertEquals(100, observer.values().first().size)
//        assertEquals("2103123", observer.values().first().first().firstItemMilSec)
//    }
//
//    @Test
//    fun `test saveSubListToDB() success with clear table == true`() {
//        val observer = TestObserver<Unit>()
//        sourceSubscriptionListRepository.saveSubListToDB(
//                subscriptionSourceListResponseModel = generateSourceListResponse(),
//                clearOldTable = true
//        ).subscribe(observer)
//        observer.assertSubscribed()
//        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
//        verify(mockSubscriptionDao).clearTable()
//        verify(mockSubscriptionDao).insertAll(com.nhaarman.mockitokotlin2.any())
//        observer.assertComplete()
//    }
//
//    @Test
//    fun `test saveSubListToDB() success with clear table == false`() {
//        val observer = TestObserver<Unit>()
//        sourceSubscriptionListRepository.saveSubListToDB(
//                subscriptionSourceListResponseModel = generateSourceListResponse(),
//                clearOldTable = false
//        ).subscribe(observer)
//        observer.assertSubscribed()
//        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)
//        verify(mockSubscriptionDao, times(0)).clearTable()
//        verify(mockSubscriptionDao).insertAll(com.nhaarman.mockitokotlin2.any())
//        observer.assertComplete()
//    }

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
            Single.fromCallable(::generateSourceListResponse)
        )

        `when`(mockRoomService.subscriptionDao()).thenReturn(mockSubscriptionDao)

        `when`(mockSubscriptionDao.getAll()).thenReturn(
            Single.fromCallable(::generateSubscriptionListEntity)
        )

    }

    private fun generateSubscriptionListEntity(): List<com.haomins.data.model.entities.SubscriptionEntity> {
        val subscriptionEntityList = mutableListOf<com.haomins.data.model.entities.SubscriptionEntity>()
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