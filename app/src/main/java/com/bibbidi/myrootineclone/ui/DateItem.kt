package com.bibbidi.myrootineclone.ui

import com.bibbidi.myrootineclone.ui.customview.DateState
import com.bibbidi.myrootineclone.ui.customview.DayOfTheWeek

data class DateItem(
    val checked: Boolean,
    val dateState: DateState,
    val dayOfTheMonth: Int,
    val dayOfTheWeek: DayOfTheWeek,
    val isToday: Boolean
)
