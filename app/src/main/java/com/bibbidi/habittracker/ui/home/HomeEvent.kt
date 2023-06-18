package com.bibbidi.habittracker.ui.home

import com.bibbidi.habittracker.ui.model.habit.log.HabitLogUiModel
import org.threeten.bp.LocalDate

sealed class HomeEvent {

    object ShowSelectHabitType : HomeEvent()

    object SuccessAddHabit : HomeEvent()

    data class ShowDatePicker(val date: LocalDate) : HomeEvent()

    object ShowTrackValueDialog : HomeEvent()

    data class AttemptDeleteHabit(val habitLog: HabitLogUiModel) : HomeEvent()
}
