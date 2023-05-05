package com.bibbidi.habittracker.ui.home.habits

sealed interface HabitItem {
    val id: Long
    val order: Int
    val emoji: String
    val name: String
    val isAlarm: Boolean
    val whenRun: String
}
