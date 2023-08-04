package com.bibbidi.habittracker.domain.model

import org.threeten.bp.LocalDate

data class HabitWithLogs(
    val habit: Habit,
    val habitLogs: LinkedHashMap<LocalDate, HabitLog>
)
