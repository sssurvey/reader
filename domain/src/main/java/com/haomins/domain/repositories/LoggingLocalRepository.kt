package com.haomins.domain.repositories

import java.io.File

interface LoggingLocalRepository {

    fun getLogFile(): File

}