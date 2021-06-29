package com.haomins.domain.repositories

import java.io.File

interface LoggingRepositoryContract {

    fun getLogFile(): File

}