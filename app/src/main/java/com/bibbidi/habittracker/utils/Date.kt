package com.bibbidi.habittracker.utils

import com.bibbidi.habittracker.ui.common.Constants.ONE_WEEK
import com.prolificinteractive.materialcalendarview.CalendarDay
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate

fun LocalDate.isSameWeek(other: LocalDate) = getStartOfTheWeek() == other.getStartOfTheWeek()

fun LocalDate.getStartOfTheWeek(): LocalDate {
    val daysUntilFirstDay = (dayOfWeek.value - DayOfWeek.SUNDAY.value + ONE_WEEK) % ONE_WEEK
    return minusDays(daysUntilFirstDay.toLong())
}

fun LocalDate.isSameYearAndMonth(other: LocalDate) =
    monthValue == other.monthValue && year == other.year

fun LocalDate.asCalendarDay() = CalendarDay.from(year, monthValue - 1, dayOfMonth)

fun CalendarDay.asLocalDate() = LocalDate.of(year, month + 1, day)
