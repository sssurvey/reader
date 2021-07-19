package com.haomins.data.repositories

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

class DisclosureRepositoryTest {

    @Mock
    lateinit var mockAndroidService: AndroidService

    private lateinit var disclosureRepository: DisclosureRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        disclosureRepository = DisclosureRepository(
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

        disclosureRepository
            .loadDisclosureContent()
            .subscribeWith(testObserver)

        testObserver.assertSubscribed()
        testObserver.assertComplete()

        assertTrue(testObserver.values().first() == testString)
    }
}