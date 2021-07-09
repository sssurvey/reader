package com.haomins.data.repositories

import android.app.Application
import com.haomins.data.service.AndroidService
import com.haomins.data.util.DateUtils
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import java.io.ByteArrayInputStream
import java.io.File

class LoggingRepositoryTest {

    @Mock
    lateinit var mockDateUtils: DateUtils

    @Mock
    lateinit var mockApplication: Application

    @Mock
    lateinit var mockAndroidService: AndroidService

    private lateinit var loggingRepository: LoggingRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        loggingRepository = LoggingRepository(
            mockDateUtils,
            mockAndroidService
        )
    }

    @Test
    fun getLogFile() {

        val tempFile = File.createTempFile("log", "")
        val mockFileDir = mock<File>()
        val mockPid = 123
        val mockRuntime = mock<Runtime>()
        val mockProcess = mock<Process>()
        val mockInputStream = ByteArrayInputStream("test data".toByteArray())

        fun mock() {
            `when`(mockDateUtils.getCurrentDate()).thenReturn("1984-1-1")
            `when`(mockAndroidService.getInternalFileDir()).thenReturn(mockFileDir)
            `when`(mockAndroidService.createFile(any(), any())).thenReturn(tempFile)
            `when`(mockAndroidService.getProcessId()).thenReturn(mockPid)
            `when`(mockAndroidService.getSystemRuntime()).thenReturn(mockRuntime)
            `when`(mockRuntime.exec(any<String>())).thenReturn(mockProcess)
            `when`(mockProcess.inputStream).thenReturn(mockInputStream)
        }

        mock()

        loggingRepository.getLogFile()

        verify(mockRuntime, times(1)).exec(String.format("logcat -d -v threadtime *:*"))
        verify(mockRuntime, times(1)).exec("logcat -c")
    }
}