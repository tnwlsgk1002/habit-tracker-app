package com.bibbidi.habittracker.utils

import android.content.Context
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.utils.Constants.ONE_WEEK
import com.prolificinteractive.materialcalendarview.CalendarDay
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

fun LocalDate.isSameWeek(other: LocalDate) = getStartOfTheWeek() == other.getStartOfTheWeek()

fun LocalDate.getStartOfTheWeek(): LocalDate {
    val daysUntilFirstDay = (dayOfWeek.value - DayOfWeek.SUNDAY.value + ONE_WEEK) % ONE_WEEK
    return minusDays(daysUntilFirstDay.toLong())
}

fun LocalDate.isSameYearAndMonth(other: LocalDate) =
    monthValue == other.monthValue && year == other.year

fun LocalDate.asCalendarDay() = CalendarDay.from(year, monthValue - 1, dayOfMonth)

fun CalendarDay.asLocalDate() = LocalDate.of(year, month + 1, day)

fun convertTo24HourFormat(hour: Int, minute: Int, isPm: Boolean = false): LocalTime {
    val formattedHour = if (isPm) (hour % 12) + 12 else hour % 12
    return LocalTime.of(formattedHour, minute)
}

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
