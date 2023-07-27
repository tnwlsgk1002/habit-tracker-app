package com.bibbidi.habittracker.data.model.habit

import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

data class Habit(
    val id: Long?,
    val name: String,
    val emoji: String,
    val alarmTime: LocalTime?,
    val repeatsDayOfTheWeeks: Set<DayOfWeek>,
    val startDate: LocalDate
)
