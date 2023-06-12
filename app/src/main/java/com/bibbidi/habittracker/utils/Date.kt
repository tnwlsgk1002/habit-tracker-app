package com.bibbidi.habittracker.utils

import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate

fun LocalDate.isSameWeek(other: LocalDate) = getStartOfTheWeek() == other.getStartOfTheWeek()

fun LocalDate.getStartOfTheWeek(): LocalDate {
    val daysUntilFirstDay = (dayOfWeek.value - DayOfWeek.SUNDAY.value + 7) % 7
    return minusDays(daysUntilFirstDay.toLong())
}
