package com.bibbidi.habittracker.domain.model.log

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

sealed interface HabitLog {
    val logId: Long?
    val habitId: Long?
    val habitTypeId: Long?
    val date: LocalDate
    val emoji: String
    val name: String
    val alarmTime: LocalTime?
    val whenRun: String
}
