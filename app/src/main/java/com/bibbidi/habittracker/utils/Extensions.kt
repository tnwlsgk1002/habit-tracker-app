package com.bibbidi.habittracker.utils

import android.text.Editable
import android.view.View
import android.widget.EditText
import org.threeten.bp.Duration
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

fun LocalTime.toGoalTimeString(): String {
    return if (hour == 0 && minute == 0) {
        "0s"
    } else if (hour == 0) {
        "${minute}s"
    } else if (minute == 0) {
        "${hour}h"
    } else {
        "${hour}h ${minute}s"
    }
}

fun Duration.toGoalTimeString(): String {
    val hour = toHoursPart()
    val minute = toMinutesPart()
    return if (hour == 0 && minute == 0) {
        "0s"
    } else if (hour == 0) {
        "${minute}s"
    } else if (minute == 0) {
        "${hour}h"
    } else {
        "${hour}h ${minute}s"
    }
}

fun Long.asLocalDate(): LocalDate {
    val instance = Instant.ofEpochMilli(this)
    return instance.atZone(ZoneId.systemDefault()).toLocalDate()
}

fun LocalDate.asLong(): Long {
    val dateTime = atStartOfDay()
    val zoneDateTime = ZonedDateTime.of(dateTime, ZoneId.systemDefault())
    return zoneDateTime.toInstant().toEpochMilli()
}

fun Int?.toTwoDigits(): String {
    return String.format("%02d", this ?: 0)
}

fun Editable?.toTwoDigits(): String {
    val number = toString().toInt()
    return number.toTwoDigits()
}

fun EditText.toFixToTwoDigits() {
    onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
        if (!hasFocus) {
            setText(text.toTwoDigits())
        }
    }
}
