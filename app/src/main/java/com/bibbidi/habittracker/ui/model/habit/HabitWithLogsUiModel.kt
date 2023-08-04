package com.bibbidi.habittracker.ui.model.habit

import org.threeten.bp.LocalDate

data class HabitWithLogsUiModel(
    val habit: HabitUiModel,
    val habitLogs: LinkedHashMap<LocalDate, HabitLogUiModel>
)
