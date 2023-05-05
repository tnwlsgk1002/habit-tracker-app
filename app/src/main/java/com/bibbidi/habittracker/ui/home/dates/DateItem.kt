package com.bibbidi.habittracker.ui.home.dates

import com.bibbidi.habittracker.ui.customview.DayOfTheWeek

data class DateItem(
    val checked: Boolean,
    val dayOfTheMonth: Int,
    val dayOfTheWeek: DayOfTheWeek,
    val isToday: Boolean
)
