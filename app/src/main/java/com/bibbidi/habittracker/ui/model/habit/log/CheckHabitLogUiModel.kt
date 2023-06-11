package com.bibbidi.habittracker.ui.model.habit.log

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

data class CheckHabitLogUiModel(
    override val logId: Long?,
    override val parentId: Long?,
    override val emoji: String,
    override val date: LocalDate,
    override val name: String,
    override val alarmTime: LocalTime?,
    override val whenRun: String,
    val isChecked: Boolean
) : HabitLogUiModel
