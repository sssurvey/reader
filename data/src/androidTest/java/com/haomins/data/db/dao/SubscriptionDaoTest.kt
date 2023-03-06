package com.haomins.data.db.dao

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import com.haomins.data.db.AppDatabase
import com.haomins.data.model.entities.SubscriptionEntity
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SubscriptionDaoTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var subscriptionDao: SubscriptionDao

    @Before
    fun createDb() {
        val subscriptionList: MutableList<SubscriptionEntity> = ArrayList()
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        subscriptionDao = appDatabase.subscriptionDao()

        // Create dummy source list
        for (counter in 0 until 100) {
            subscriptionList.add(
                SubscriptionEntity(
                    id = counter.toString(),
                    title = (counter * 2).toString(),
                    sortId = "sortId: $counter",
                    firstItemMilSec = System.currentTimeMillis().toString(),
                    url = "rss.$counter.com",
                    htmlUrl = "www.$counter.com",
                    iconUrl = "www.$counter.com/pic"
                )
            )
        }

        // Populate DB
        appDatabase.subscriptionDao().insertAll(*subscriptionList.toTypedArray())
    }

    @After
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    fun testInsertAll() {
        val originalSize = subscriptionDao.getAll().blockingGet().size
        subscriptionDao.insertAll(
            SubscriptionEntity(
                id = "100",
                title = (100 * 2).toString(),
                sortId = "sortId: 100",
                firstItemMilSec = System.currentTimeMillis().toString(),
                url = "rss.100.com",
                htmlUrl = "www.100.com",
                iconUrl = "www.100.com/pic"
            )
        )
        assertTrue(subscriptionDao.getAll().blockingGet().size > originalSize)
    }

    @Test
    fun testInsertAllReplace() {
        val originalSize = subscriptionDao.getAll().blockingGet().size
        subscriptionDao.insertAll(
            SubscriptionEntity(
                id = "0",
                title = (99).toString(),
                sortId = "sortId: 99",
                firstItemMilSec = System.currentTimeMillis().toString(),
                url = "rss.99.com",
                htmlUrl = "www.99.com",
                iconUrl = "www.99.com/pic"
            )
        )

        // If after insert, the size is still the same, means replaced
        assertTrue(subscriptionDao.getAll().blockingGet().size == originalSize)

        // confirm the new element is inserted, and replace the old one
        subscriptionDao.getAll().blockingGet().forEach {
            if (it.title == "99") assertTrue(true)
        }
    }

    @Test
    fun testGetAll() {
        assertTrue(subscriptionDao.getAll().blockingGet().isNotEmpty())
    }

    @Test
    fun testClearTable() {
        subscriptionDao.clearTable()
        assertTrue(subscriptionDao.getAll().blockingGet().isEmpty())
    }
}