package com.bibbidi.habittracker.ui.home

import org.threeten.bp.LocalDate

sealed class HomeEvent {

    object ShowSelectHabitType : HomeEvent()

    object SuccessAddHabit : HomeEvent()

    data class ShowDatePicker(val date: LocalDate) : HomeEvent()

    object ShowTrackValueDialog : HomeEvent()
}
