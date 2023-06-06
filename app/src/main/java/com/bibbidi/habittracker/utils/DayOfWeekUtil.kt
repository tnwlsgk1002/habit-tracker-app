package com.bibbidi.habittracker.utils

import android.content.Context
import com.bibbidi.habittracker.R
import org.threeten.bp.DayOfWeek

fun DayOfWeek.getStringResource(context: Context) = context.getString(
    when (this) {
        DayOfWeek.SUNDAY -> R.string.sunday
        DayOfWeek.MONDAY -> R.string.monday
        DayOfWeek.TUESDAY -> R.string.tuesday
        DayOfWeek.WEDNESDAY -> R.string.wednesday
        DayOfWeek.THURSDAY -> R.string.thursday
        DayOfWeek.FRIDAY -> R.string.friday
        DayOfWeek.SATURDAY -> R.string.saturday
    }
)

val dayOfWeekValues = arrayOf(
    DayOfWeek.SUNDAY,
    DayOfWeek.MONDAY,
    DayOfWeek.TUESDAY,
    DayOfWeek.WEDNESDAY,
    DayOfWeek.THURSDAY,
    DayOfWeek.FRIDAY,
    DayOfWeek.SATURDAY
)
