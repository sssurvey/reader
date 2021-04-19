package com.haomins.reader.utils

import android.app.Application
import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class DateUtils @Inject constructor(
        private val application: Application
) {

    companion object {
        private const val DEFAULT_TIME_FORMAT = "h:mm a" // 1:30 PM, no leading zero for hours
        private const val ONE_THOUSAND_MILLISECOND = 1000L
    }

    private val formatter by lazy {
        SimpleDateFormat(DEFAULT_TIME_FORMAT, application.resources.configuration.locales[0])
    }

    private val timeZone by lazy {
        Calendar.getInstance().timeZone
    }

    fun to24HrString(unixTimeStamp: Long): String {
        formatter.timeZone = timeZone
        return formatter.format(unixTimeStamp * ONE_THOUSAND_MILLISECOND)

    }

    fun howLongAgo(unixTimeStamp: Long): String {
        return DateUtils.getRelativeTimeSpanString(
                unixTimeStamp * ONE_THOUSAND_MILLISECOND,
                Calendar.getInstance().timeInMillis,
                DateUtils.MINUTE_IN_MILLIS
        ).toString()
    }
}