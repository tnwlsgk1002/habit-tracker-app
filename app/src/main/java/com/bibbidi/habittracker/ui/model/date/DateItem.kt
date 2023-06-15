package com.bibbidi.habittracker.ui.model.date

import com.bibbidi.habittracker.ui.common.Constants.MAX_DAY_OF_WEEK_IDX
import com.bibbidi.habittracker.ui.common.Constants.MIN_DAY_OF_WEEK_IDX
import com.bibbidi.habittracker.utils.getStartOfTheWeek
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.Month

data class DateItem(
    val checked: Boolean,
    val year: Int,
    val month: Month,
    val dayOfTheMonth: Int,
    val dayOfWeek: DayOfWeek,
    val isToday: Boolean
) {
    val date: LocalDate = LocalDate.of(year, month, dayOfTheMonth)

    companion object DateItemFactory {

        fun createDateItem(checkedDate: LocalDate, date: LocalDate, now: LocalDate): DateItem {
            return DateItem(
                checkedDate == date,
                date.year,
                date.month,
                date.dayOfMonth,
                date.dayOfWeek,
                date.isEqual(now)
            )
        }
    }
}

fun getDateItemsByDate(checkedDate: LocalDate): Array<DateItem> {
    val now = LocalDate.now()
    val startDate = checkedDate.getStartOfTheWeek()
    return (MIN_DAY_OF_WEEK_IDX..MAX_DAY_OF_WEEK_IDX).map {
        val date = startDate.plusDays(it.toLong())
        DateItem.createDateItem(checkedDate, date, now)
    }.toTypedArray()
}
