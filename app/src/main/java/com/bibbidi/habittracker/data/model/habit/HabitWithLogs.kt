package com.bibbidi.habittracker.data.model.habit

import org.threeten.bp.LocalDate

data class HabitWithLogs(
    val habit: Habit,
    val habitLogs: LinkedHashMap<LocalDate, HabitLog>
)
