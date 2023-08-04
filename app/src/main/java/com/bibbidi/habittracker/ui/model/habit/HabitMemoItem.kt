package com.bibbidi.habittracker.ui.model.habit

import org.threeten.bp.LocalDate

data class HabitMemoItem(
    val logId: Long?,
    val habitId: Long?,
    val date: LocalDate,
    val isHeader: Boolean = false,
    val memo: String?
)
