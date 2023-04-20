package com.haomins.data.datastore.local

import com.haomins.data.db.dao.SubscriptionDao
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class SubscriptionLocalDataStoreTest {

    @Mock
    lateinit var subscriptionDao: SubscriptionDao

    private lateinit var subscriptionLocalDataStore: SubscriptionLocalDataStore

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        subscriptionLocalDataStore = SubscriptionLocalDataStore(
            subscriptionDao
        )
    }

    @Test
    fun saveAllSubscriptionToLocal() {
        val testList = generateSubscriptionListEntity()
        `when`(subscriptionDao.insertAll(testList)).thenReturn(Completable.complete())
        subscriptionLocalDataStore.saveAllSubscriptionToLocal(
            testList
        ).blockingAwait()
        verify(subscriptionDao, times(1)).insertAll(testList)
    }

    @Test
    fun loadAllSubscriptionFromLocal() {
        val testList = generateSubscriptionListEntity()
        `when`(subscriptionDao.getAll()).thenReturn(Single.just(testList))
        val list = subscriptionLocalDataStore.loadAllSubscriptionFromLocal().blockingGet()
        assert(list.size == testList.size)
    }

    @Test
    fun `clearAllSubscriptions should invoke clearTable on dao`() {
        `when`(subscriptionDao.clearTable()).thenReturn(Completable.complete())
        subscriptionLocalDataStore.clearAllSubscriptions().blockingAwait()
        verify(subscriptionDao, times(1)).clearTable()
    }

    private fun generateSubscriptionListEntity(): List<com.haomins.model.entity.SubscriptionEntity> {
        val subscriptionEntityList =
            mutableListOf<com.haomins.model.entity.SubscriptionEntity>()
        for (i in 0 until 100) {
            subscriptionEntityList.add(
                com.haomins.model.entity.SubscriptionEntity(
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
}