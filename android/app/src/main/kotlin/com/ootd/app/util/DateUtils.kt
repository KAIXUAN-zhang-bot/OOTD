package com.ootd.app.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA)
    private val dateOnlyFormat = SimpleDateFormat("yyyy-MM-dd", Locale.CHINA)

    fun formatDate(timestamp: Long): String {
        return dateFormat.format(Date(timestamp))
    }

    fun formatDateOnly(timestamp: Long): String {
        return dateOnlyFormat.format(Date(timestamp))
    }

    fun getDaysSince(timestamp: Long): Long {
        val now = System.currentTimeMillis()
        return (now - timestamp) / (1000 * 60 * 60 * 24)
    }
}
