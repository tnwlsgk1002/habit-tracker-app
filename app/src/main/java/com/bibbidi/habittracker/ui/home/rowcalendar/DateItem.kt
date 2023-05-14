package com.bibbidi.habittracker.ui.home.rowcalendar

import com.bibbidi.habittracker.ui.model.DayOfTheWeek

data class DateItem(
    val checked: Boolean,
    val dayOfTheMonth: Int,
    val dayOfTheWeek: DayOfTheWeek,
    val isToday: Boolean
)
