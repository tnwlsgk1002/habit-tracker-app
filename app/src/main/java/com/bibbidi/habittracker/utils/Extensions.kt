package com.bibbidi.habittracker.utils

import android.graphics.drawable.ColorDrawable
import android.text.Editable
import android.view.View
import android.widget.EditText
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.threeten.bp.Duration
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

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

fun String.toTwoDigits(): String = padStart(2, '0')

fun Int.toTwoDigits(): String = toString().toTwoDigits()

fun Editable.toTwoDigits(): String = toString().toTwoDigits()

fun EditText.toFixToTwoDigits() {
    onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
        if (!hasFocus) {
            val paddedText = text.toTwoDigits()
            setText(paddedText)
        }
    }
}

fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
    }
}

fun View.getBackgroundColor(): Int? {
    return (background as? ColorDrawable)?.color
}
