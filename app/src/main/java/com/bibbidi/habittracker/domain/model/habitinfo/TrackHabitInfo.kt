package com.bibbidi.habittracker.domain.model.habitinfo

import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

data class TrackHabitInfo(
    override val habitId: Long?,
    override val childId: Long?,
    override val name: String,
    override val emoji: String,
    override val alarmTime: LocalTime?,
    override val whenRun: String,
    override val repeatsDayOfTheWeeks: Set<DayOfWeek>,
    override val startDate: LocalDate
) : HabitInfo
