package com.aashish.bookshelf.utils

import android.os.Build
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.Calendar

object DateUtils {

    fun epochToYear(epochInSeconds: Long): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // For API level >= 26, use java.time API
            val instant = Instant.ofEpochSecond(epochInSeconds)
            val zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault())
            zonedDateTime.year
        } else {
            // For API level < 26, use Calendar
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = epochInSeconds * 1000
            calendar.get(Calendar.YEAR)
        }
    }
}