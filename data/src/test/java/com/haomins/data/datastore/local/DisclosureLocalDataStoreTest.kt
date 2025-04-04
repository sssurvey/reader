package com.haomins.data.datastore.local

import com.haomins.data.service.AndroidService
import io.reactivex.observers.TestObserver
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import java.io.ByteArrayInputStream

class DisclosureLocalDataStoreTest {

    @Mock
    lateinit var mockAndroidService: AndroidService

    private lateinit var disclosureLocalDataStore: DisclosureLocalDataStore

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        disclosureLocalDataStore = DisclosureLocalDataStore(
            mockAndroidService
        )
    }

    @Test
    fun `test loadDisclosureContent successful`() {

        val testString = "test data"
        val mockInputStream = ByteArrayInputStream(testString.toByteArray())

        fun mock() {
            `when`(mockAndroidService.loadAsset(any())).thenReturn(mockInputStream)
        }

        mock()

        val testObserver = TestObserver<String>()

        disclosureLocalDataStore
            .loadDisclosureContent()
            .subscribeWith(testObserver)

        testObserver.assertSubscribed()
        testObserver.assertComplete()

        assertTrue(testObserver.values().first() == testString)
    }
}