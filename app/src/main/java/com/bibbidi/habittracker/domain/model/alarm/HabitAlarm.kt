package com.bibbidi.habittracker.domain.model.alarm

import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

data class HabitAlarm(
    val habitId: Long?,
    val name: String,
    val emoji: String,
    val alarmTime: LocalTime?,
    val repeatsDayOfTheWeeks: Set<DayOfWeek>,
    val startDate: LocalDate
)
