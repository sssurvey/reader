package com.haomins.data.util

import android.app.Application
import com.haomins.domain.common.DateUtils
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject
import android.text.format.DateUtils as AndroidDateUtil

class DateUtilsImpl @Inject constructor(
    private val application: Application
) : DateUtils {

    companion object {
        private const val DEFAULT_TIME_FORMAT =
            "h:mm a" // 1:30 PM, no leading zero for hours
        private const val DEFAULT_DATE_FORMAT =
            "EEEEE dd MMMMM yyyy HH:mm:ss.SSSZ"
        private const val ONE_THOUSAND_MILLISECOND = 1000L
    }

    private val calendar by lazy {
        Calendar.getInstance()
    }

    override fun to24HrString(unixTimeStamp: Long): String {
        val formatter =
            SimpleDateFormat(
                DEFAULT_TIME_FORMAT,
                application.resources.configuration.locales[0]
            )
        formatter.timeZone = calendar.timeZone
        return formatter.format(unixTimeStamp * ONE_THOUSAND_MILLISECOND)
            .trimEnd()
    }

    override fun howLongAgo(unixTimeStamp: Long): String {
        return AndroidDateUtil.getRelativeTimeSpanString(
            unixTimeStamp * ONE_THOUSAND_MILLISECOND,
            Calendar.getInstance().timeInMillis,
            AndroidDateUtil.MINUTE_IN_MILLIS
        ).toString().trimEnd()
    }

    override fun getCurrentDate(): String {
        val formatter =
            SimpleDateFormat(
                DEFAULT_DATE_FORMAT,
                application.resources.configuration.locales[0]
            )
        return formatter.format(calendar.time).trimEnd()
    }
}