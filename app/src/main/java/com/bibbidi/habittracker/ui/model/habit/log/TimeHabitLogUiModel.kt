package com.bibbidi.habittracker.ui.model.habit.log

import org.threeten.bp.Duration
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

data class TimeHabitLogUiModel(
    override val logId: Long?,
    override val parentId: Long?,
    override val emoji: String,
    override val name: String,
    override val date: LocalDate,
    override val alarmTime: LocalTime?,
    override val whenRun: String,
    val goalDuration: Duration,
    val cntDuration: Duration,
    val isStarted: Boolean
) : HabitLogUiModel
