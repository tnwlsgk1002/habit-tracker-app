package com.bibbidi.habittracker.domain.model

import org.threeten.bp.LocalDate

data class HabitMemo(
    val logId: Long?,
    val habitId: Long?,
    val date: LocalDate,
    val memo: String?
)
