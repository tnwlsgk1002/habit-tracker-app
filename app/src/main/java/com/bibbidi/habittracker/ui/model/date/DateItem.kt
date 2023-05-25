package com.bibbidi.habittracker.ui.model.date

import com.bibbidi.habittracker.ui.model.DayOfTheWeekUiModel

data class DateItem(
    val checked: Boolean,
    val dayOfTheMonth: Int,
    val dayOfTheWeek: DayOfTheWeekUiModel,
    val isToday: Boolean
)
