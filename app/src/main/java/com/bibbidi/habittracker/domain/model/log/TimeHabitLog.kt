package com.bibbidi.habittracker.domain.model.log

import org.threeten.bp.Duration
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

data class TimeHabitLog(
    override val logId: Long?,
    override val habitId: Long?,
    override val habitTypeId: Long?,
    override val date: LocalDate,
    override val emoji: String,
    override val name: String,
    override val alarmTime: LocalTime?,
    override val whenRun: String,
    val goalDuration: Duration,
    val cntDuration: Duration
) : HabitLog
