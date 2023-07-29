package com.bibbidi.habittracker.ui.detailhabit

import com.bibbidi.habittracker.ui.model.habit.HabitUiModel

sealed class DetailHabitEvent {
    data class ShowDeleteHabit(val habit: HabitUiModel) : DetailHabitEvent()
    data class ShowUpdateHabit(val habit: HabitUiModel) : DetailHabitEvent()
}
