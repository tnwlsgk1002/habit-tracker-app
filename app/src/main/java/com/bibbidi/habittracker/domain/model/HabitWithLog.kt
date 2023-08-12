package com.bibbidi.habittracker.domain.model

data class HabitWithLog(
    val habit: Habit,
    val habitLog: HabitLog
)
