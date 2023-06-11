package com.bibbidi.habittracker.ui.model.date

import org.threeten.bp.DayOfWeek
import org.threeten.bp.Month

data class DateItem(
    val checked: Boolean,
    val year: Int,
    val month: Month,
    val dayOfTheMonth: Int,
    val dayOfWeek: DayOfWeek,
    val isToday: Boolean
)
