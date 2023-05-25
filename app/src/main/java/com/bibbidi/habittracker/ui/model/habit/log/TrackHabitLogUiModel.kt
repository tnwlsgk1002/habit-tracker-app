package com.bibbidi.habittracker.ui.model.habit.log

data class TrackHabitLogUiModel(
    override val id: Long,
    override val order: Int,
    override val emoji: String,
    override val name: String,
    override val isAlarm: Boolean,
    override val whenRun: String,
    val value: Long?
) : HabitLogUiModel