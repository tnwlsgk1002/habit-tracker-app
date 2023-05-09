package com.bibbidi.habittracker.ui.home.habits

import org.threeten.bp.LocalTime

data class TimeHabitItem(
    override val id: Long,
    override val order: Int,
    override val emoji: String,
    override val name: String,
    override val isAlarm: Boolean,
    override val whenRun: String,
    val goalTime: LocalTime,
    val cntTime: LocalTime,
    val isStarted: Boolean
) : HabitItem