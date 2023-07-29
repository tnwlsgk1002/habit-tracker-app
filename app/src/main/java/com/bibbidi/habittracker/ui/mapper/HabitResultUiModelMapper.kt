package com.bibbidi.habittracker.ui.mapper

import com.bibbidi.habittracker.data.model.habit.HabitWithLogs
import com.bibbidi.habittracker.ui.model.habit.HabitResultUiModel

fun HabitWithLogs.HabitResult.asUiModel() = HabitResultUiModel(cntNumber, bestNumber)
