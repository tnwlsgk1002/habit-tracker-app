package com.bibbidi.habittracker.ui

sealed class MainEvent {

    object SuccessAddHabit : MainEvent()

    object SuccessUpdateHabit : MainEvent()

    object SuccessDeleteHabit : MainEvent()
}
