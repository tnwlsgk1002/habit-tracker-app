package com.bibbidi.habittracker.utils

import android.content.Context
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.utils.Constants.MAX_HOUR
import com.bibbidi.habittracker.utils.Constants.ONE_WEEK
import com.prolificinteractive.materialcalendarview.CalendarDay
import org.threeten.bp.DayOfWeek
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

fun LocalDate.isSameWeek(other: LocalDate) = getStartOfTheWeek() == other.getStartOfTheWeek()

fun LocalDate.getStartOfTheWeek(): LocalDate {
    val daysUntilFirstDay = (dayOfWeek.value - DayOfWeek.SUNDAY.value + ONE_WEEK) % ONE_WEEK
    return minusDays(daysUntilFirstDay.toLong())
}
fun convertTo24HourFormat(hour: Int, minute: Int, isPm: Boolean = false): LocalTime {
    val formattedHour = if (isPm) (hour % MAX_HOUR) + MAX_HOUR else hour % MAX_HOUR
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
        sortedBy { if (it.value == ONE_WEEK) 0 else it.value }
    }
}

fun LocalDate.asCalendarDay() = CalendarDay.from(year, monthValue - 1, dayOfMonth)

fun CalendarDay.asLocalDate() = LocalDate.of(year, month + 1, day)

fun Long.asLocalDate(): LocalDate {
    val instance = Instant.ofEpochMilli(this)
    return instance.atZone(ZoneId.systemDefault()).toLocalDate()
}

fun LocalDate.asLong(): Long {
    val dateTime = atStartOfDay()
    val zoneDateTime = ZonedDateTime.of(dateTime, ZoneId.systemDefault())
    return zoneDateTime.toInstant().toEpochMilli()
}
