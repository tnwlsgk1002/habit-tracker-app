package com.bibbidi.habittracker.ui.home

import com.bibbidi.habittracker.ui.model.habit.HabitUiModel
import org.threeten.bp.LocalDate

sealed class HomeEvent {
    data class ShowDatePicker(val date: LocalDate) : HomeEvent()

    data class ShowAddHabit(val habitInfo: HabitUiModel) : HomeEvent()

    data class ShowDeleteHabit(val habitLog: HabitUiModel) : HomeEvent()

    data class ShowUpdateHabit(val habitInfo: HabitUiModel) : HomeEvent()
}
