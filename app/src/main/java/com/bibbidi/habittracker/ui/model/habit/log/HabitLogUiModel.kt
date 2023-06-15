package com.bibbidi.habittracker.ui.model.habit.log

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

sealed interface HabitLogUiModel {
    val logId: Long?
    val parentId: Long?
    val emoji: String
    val name: String
    val date: LocalDate
    val alarmTime: LocalTime?
    val whenRun: String
}
