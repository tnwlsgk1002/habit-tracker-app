package com.bibbidi.habittracker.ui.model.habit.log

sealed interface HabitLogUiModel {
    val id: Long
    val order: Int
    val emoji: String
    val name: String
    val isAlarm: Boolean
    val whenRun: String
}
