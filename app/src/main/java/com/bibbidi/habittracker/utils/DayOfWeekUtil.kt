package com.bibbidi.habittracker.utils

import android.content.Context
import com.bibbidi.habittracker.R
import org.threeten.bp.DayOfWeek

fun DayOfWeek.getStringResource(context: Context, isShort: Boolean = true): String {
    val index = dayOfWeekValues.indexOf(this)
    return context.resources.getStringArray(
        if (isShort) {
            R.array.short_weekdays
        } else {
            R.array.full_weekdays
        }
    )[index]
}

val dayOfWeekValues = arrayOf(
    DayOfWeek.SUNDAY,
    DayOfWeek.MONDAY,
    DayOfWeek.TUESDAY,
    DayOfWeek.WEDNESDAY,
    DayOfWeek.THURSDAY,
    DayOfWeek.FRIDAY,
    DayOfWeek.SATURDAY
)

fun Collection<DayOfWeek>.sortDaysOfWeek(isFirstMonday: Boolean = false): Collection<DayOfWeek> {
    return if (isFirstMonday) {
        sortedBy { it.value }
    } else {
        sortedBy { if (it.value == Constants.ONE_WEEK) 0 else it.value }
    }
}
