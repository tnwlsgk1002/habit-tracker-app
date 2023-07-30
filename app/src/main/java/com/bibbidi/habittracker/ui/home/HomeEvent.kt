package com.bibbidi.habittracker.ui.home

import com.bibbidi.habittracker.ui.model.habit.HabitUiModel
import com.bibbidi.habittracker.ui.model.habit.HabitWithLogUiModel
import org.threeten.bp.LocalDate

sealed class HomeEvent {
    data class ShowDatePicker(val date: LocalDate) : HomeEvent()

    data class ShowAddHabit(val habit: HabitUiModel) : HomeEvent()

    data class ShowDeleteHabit(val habit: HabitUiModel) : HomeEvent()

    data class ShowUpdateHabit(val habit: HabitUiModel) : HomeEvent()

    data class ShowMemoEdit(val habitLog: HabitWithLogUiModel) : HomeEvent()
}
