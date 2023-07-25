package com.bibbidi.habittracker.data.model.habit

import org.threeten.bp.LocalDate

data class HabitLog(
    val id: Long?,
    val habitInfo: Habit,
    val date: LocalDate,
    val isCompleted: Boolean,
    val memo: String?
)
