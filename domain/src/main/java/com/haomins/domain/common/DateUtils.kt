package com.haomins.domain.common

interface DateUtils {

    fun to24HrString(unixTimeStamp: Long): String

    fun howLongAgo(unixTimeStamp: Long): String

    fun getCurrentDate(): String

}