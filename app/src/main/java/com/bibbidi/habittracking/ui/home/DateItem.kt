package com.bibbidi.habittracking.ui.home

import com.bibbidi.habittracking.ui.customview.DayOfTheWeek

data class DateItem(
    val checked: Boolean,
    val dayOfTheMonth: Int,
    val dayOfTheWeek: DayOfTheWeek,
    val isToday: Boolean
)
