package com.haomins.data.datastore.local

import com.haomins.data.util.ContextUtils
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.verify
import java.io.File

class AppCacheDataStoreTest {

    @Mock
    lateinit var mockContextUtils: ContextUtils

    @Mock
    lateinit var cacheData: File

    @Mock
    lateinit var validFileDir: File

    @Mock
    lateinit var validFileItem: File

    @Mock
    lateinit var dummyFile: File

    private lateinit var appCacheDataStore: AppCacheDataStore

    @Before
    fun setUp() {

        MockitoAnnotations.openMocks(this)
        `when`(dummyFile.length()).thenReturn(2048 * 1024)
        `when`(dummyFile.isDirectory).thenReturn(false)
        `when`(validFileItem.length()).thenReturn(4096 * 1024)
        `when`(validFileDir.isDirectory).thenReturn(true)
        `when`(validFileDir.listFiles()).thenReturn(arrayOf(dummyFile, dummyFile))
        `when`(validFileItem.isDirectory).thenReturn(false)

        val mockFileList = arrayListOf<File>().apply {
            (0..10).forEach {
                add(
                    if (it % 2 != 0) {
                        validFileDir
                    } else {
                        validFileItem
                    }
                )
            }
        }


        `when`(cacheData.listFiles()).thenReturn(mockFileList.toTypedArray())

        appCacheDataStore = AppCacheDataStore(
            mockContextUtils
        )
    }

    @Test
    fun `is AppCacheDataStore initialized correctly`() {
        assert(this::appCacheDataStore.isInitialized)
    }

    @Test
    fun `getCurrentCacheSize should return file size in MB`() {
        `when`(mockContextUtils.getCacheDir()).thenReturn(
            cacheData
        )
        val testObserver = appCacheDataStore.getCurrentCacheSize().test()
        testObserver.assertComplete()
        /*
         * 44 MB - since we provided total:
         * - 6 of 'validItemFile' each 4 MB
         * - 5 of `dummyFile` each 2 MB
         * Therefore: 6 * 4 + 5 * 2 = 44 MB
         */
        assert(testObserver.values().first() == 44L)
    }

    @Test
    fun `should clearCache`() {
        `when`(mockContextUtils.getCacheDir()).thenReturn(
            cacheData
        )
        val testObserver = appCacheDataStore.clearCache().test()
        testObserver.assertComplete()
        verify(dummyFile, times(10)).delete()
    }
}