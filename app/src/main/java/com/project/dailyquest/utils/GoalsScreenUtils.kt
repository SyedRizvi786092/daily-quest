package com.project.dailyquest.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

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

fun getFormattedDate(epoch: Long): String {
    val instant = Instant.fromEpochMilliseconds(epoch)
    val formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy")
    val date =  instant.toLocalDateTime(TimeZone.currentSystemDefault()).toJavaLocalDateTime()
    return date.format(formatter)
}

fun getDecoratedDeadline(deadline: Long): AnnotatedString {
    return buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace)
        ) {
            append("Goal Deadline: ")
        }
        append(getFormattedDate(deadline))
        withStyle(style = SpanStyle(fontWeight = FontWeight.Light,
            color = Color.Red)) {
            append(" (${getRemainingDays(deadline)} days left)")
        }
    }
}