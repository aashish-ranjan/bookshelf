package com.aashish.bookshelf.utils

import android.os.Build
import java.time.Instant
import java.util.Calendar

object DateUtils {

    fun epochToYear(epochMillis: Long): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // For API level >= 26, use java.time API
            val instant = Instant.ofEpochMilli(epochMillis)
            val zonedDateTime = java.time.ZonedDateTime.ofInstant(instant, java.time.ZoneId.systemDefault())
            zonedDateTime.year
        } else {
            // For API level < 26, use Calendar
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = epochMillis
            calendar.get(Calendar.YEAR)
        }
    }
}