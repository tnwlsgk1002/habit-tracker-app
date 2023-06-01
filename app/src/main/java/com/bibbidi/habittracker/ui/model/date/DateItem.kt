package com.bibbidi.habittracker.ui.model.date

import org.threeten.bp.DayOfWeek

data class DateItem(
    val checked: Boolean,
    val dayOfTheMonth: Int,
    val dayOfWeek: DayOfWeek,
    val isToday: Boolean
)
