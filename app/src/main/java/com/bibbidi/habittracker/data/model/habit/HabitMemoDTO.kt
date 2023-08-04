package com.bibbidi.habittracker.data.model.habit

import org.threeten.bp.LocalDate

data class HabitMemoDTO(
    val logId: Long?,
    val habitId: Long?,
    val date: LocalDate,
    val memo: String?
)
