package id.ak.mytaskmanager.common.extension

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun Long.formatAsLocalizedString(isInMillisecond: Boolean = false): String {
    val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)
    val instant = if (isInMillisecond) Instant.ofEpochMilli(this) else Instant.ofEpochSecond(this)
    val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
    return dateTime.format(formatter)
}