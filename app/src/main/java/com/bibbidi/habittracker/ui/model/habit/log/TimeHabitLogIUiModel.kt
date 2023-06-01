package com.bibbidi.habittracker.ui.model.habit.log

import org.threeten.bp.Duration

data class TimeHabitLogIUiModel(
    override val id: Long,
    override val order: Int,
    override val emoji: String,
    override val name: String,
    override val isAlarm: Boolean,
    override val whenRun: String,
    val goalDuration: Duration,
    val cntDuration: Duration,
    val isStarted: Boolean
) : HabitLogUiModel
