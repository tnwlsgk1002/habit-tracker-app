package com.bibbidi.habittracker.domain.model.habitinfo

import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

sealed interface HabitInfo {
    val habitId: Long?
    val childId: Long?
    val name: String
    val emoji: String
    val alarmTime: LocalTime?
    val whenRun: String
    val repeatsDayOfTheWeeks: Set<DayOfWeek>
    val startDate: LocalDate
}
