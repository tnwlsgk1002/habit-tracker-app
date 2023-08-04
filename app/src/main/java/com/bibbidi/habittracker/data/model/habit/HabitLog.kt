package com.bibbidi.habittracker.data.model.habit

import org.threeten.bp.LocalDate

data class HabitLog(
    val id: Long? = null,
    val habitId: Long? = null,
    val date: LocalDate,
    val isCompleted: Boolean,
    val memo: String?
)
