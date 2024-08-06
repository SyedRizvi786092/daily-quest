package com.project.dailyquest.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant

fun getRemainingDays(deadline: Long): Int {
    val deadlineInstant = Instant.fromEpochMilliseconds(deadline)
    val duration = deadlineInstant - Clock.System.now()
    return duration.inWholeDays.toInt()
}

fun getEpochTime(date: String): Long {
    val localDate = LocalDate.parse(date)
    val localDateTime = LocalDateTime(date = localDate, time = LocalTime(hour = 0, minute = 0))
    val instant = localDateTime.toInstant(TimeZone.currentSystemDefault())
    return instant.toEpochMilliseconds()
}