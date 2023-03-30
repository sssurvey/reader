package com.haomins.domain.repositories.local

import java.io.File

interface LoggingLocalRepository {

    fun getLogFile(): File

}