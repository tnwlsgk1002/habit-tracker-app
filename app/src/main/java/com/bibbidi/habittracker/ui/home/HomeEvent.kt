package com.bibbidi.habittracker.ui.home

import com.bibbidi.habittracker.ui.model.habit.HabitUiModel
import org.threeten.bp.LocalDate

sealed class HomeEvent {

    data class SuccessAddHabit(val alarmInfo: HabitUiModel) : HomeEvent()

    data class SuccessUpdateHabit(val alarmInfo: HabitUiModel) : HomeEvent()

    data class SuccessDeleteHabit(val alarmInfo: HabitUiModel) : HomeEvent()

    data class ShowDatePicker(val date: LocalDate) : HomeEvent()

    data class AttemptAddHabit(val habitInfo: HabitUiModel) : HomeEvent()

    data class AttemptDeleteHabit(val habitLog: HabitUiModel) : HomeEvent()

    data class AttemptUpdateHabit(val habitInfo: HabitUiModel) : HomeEvent()
}
