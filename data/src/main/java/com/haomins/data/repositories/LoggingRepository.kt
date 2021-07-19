package com.haomins.data.repositories

import android.util.Log
import com.haomins.data.service.AndroidService
import com.haomins.data.util.DateUtils
import com.haomins.domain.repositories.LoggingRepositoryContract
import java.io.BufferedReader
import java.io.File
import java.io.FileWriter
import java.io.InputStreamReader
import javax.inject.Inject

class LoggingRepository @Inject constructor(
    private val dateUtils: DateUtils,
    private val androidService: AndroidService
): LoggingRepositoryContract {

    companion object {
        private const val TAG = "LoggingRepository"
        private const val APP_LOG_NAME = "_reader_log"
        private const val CLEAN_UP_LOG_COMMAND = "logcat -c"
        private const val LOGGING_COMMAND = "logcat -d -v threadtime *:*"
        private const val NEW_LINE = "\n"
    }

    override fun getLogFile(): File {
        return extractLogFile()
    }

    private fun extractLogFile(): File {

        Log.d(TAG, "extractLogFile :: invoked")

        fun cleanUpLog() {
            try {
                androidService.getSystemRuntime().exec(CLEAN_UP_LOG_COMMAND)
            } catch (e: Exception) {
                Log.e(TAG, "${e.printStackTrace()}")
            }
        }

        val logFileName = dateUtils.getCurrentDate() + APP_LOG_NAME
        val file = androidService.createFile(androidService.getInternalFileDir(), logFileName)

        Log.d(TAG, "extractLogFile :: file created")

        file.deleteOnExit()

        val pid = androidService.getProcessId()

        try {

            Log.d(TAG, "extractLogFile :: generating log")

            val command = String.format(LOGGING_COMMAND)
            val recordingLog = androidService.getSystemRuntime().exec(command)
            val reader = BufferedReader(InputStreamReader(recordingLog.inputStream))
            val result = StringBuilder()
            var currentLine: String? = reader.readLine()

            Log.d(TAG, "extractLogFile called :: try write log")
            while (currentLine != null) {
                if (currentLine.contains("$pid")) {
                    result.append(currentLine)
                    result.append(NEW_LINE)
                }
                currentLine = reader.readLine()
            }

            with(FileWriter(file)) {
                Log.d(TAG, "extractLogFile :: writing to file")
                write(result.toString())
                close()
            }

        } catch (e: Exception) {
            Log.e(TAG, "extractLogFile :: ${e.printStackTrace()}")
        } finally {
            Log.d(TAG, "extractLogFile :: clean up log")
            cleanUpLog()
        }

        return file
    }

}