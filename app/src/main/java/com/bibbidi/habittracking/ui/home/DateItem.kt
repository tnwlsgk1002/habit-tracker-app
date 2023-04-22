package com.bibbidi.habittracking.ui.home

import com.bibbidi.habittracking.ui.customview.DateState
import com.bibbidi.habittracking.ui.customview.DayOfTheWeek

data class DateItem(
    val checked: Boolean,
    val dateState: DateState,
    val dayOfTheMonth: Int,
    val dayOfTheWeek: DayOfTheWeek,
    val isToday: Boolean
)
