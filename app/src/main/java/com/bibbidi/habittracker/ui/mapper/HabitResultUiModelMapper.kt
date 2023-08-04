package com.bibbidi.habittracker.ui.mapper

import com.bibbidi.habittracker.data.model.habit.HabitResult
import com.bibbidi.habittracker.ui.model.habit.HabitResultUiModel

fun HabitResult.asUiModel() = HabitResultUiModel(cntNumber, bestNumber)
