package com.bibbidi.habittracker.utils

import com.bibbidi.habittracker.ui.common.Constants.ONE_WEEK
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate

fun LocalDate.isSameWeek(other: LocalDate) = getStartOfTheWeek() == other.getStartOfTheWeek()

fun LocalDate.getStartOfTheWeek(): LocalDate {
    val daysUntilFirstDay = (dayOfWeek.value - DayOfWeek.SUNDAY.value + ONE_WEEK) % ONE_WEEK
    return minusDays(daysUntilFirstDay.toLong())
}
