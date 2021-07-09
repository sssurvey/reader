package com.haomins.data.service

import android.app.Application
import android.os.Process
import java.io.File
import javax.inject.Inject

class AndroidService @Inject constructor(
    private val application: Application
) {

    internal fun getSystemRuntime(): Runtime {
        return Runtime.getRuntime()
    }

    internal fun getProcessId(): Int {
        return Process.myPid()
    }

    internal fun getInternalFileDir(): File {
        return application.filesDir
    }

    internal fun createFile(file: File?, name: String): File {
        if (file == null) throw NullPointerException()
        return File(file, name)
    }
}