package com.bibbidi.habittracker.ui

sealed class EditHabitEvent {

    object SuccessAddHabit : EditHabitEvent()

    object SuccessUpdateHabit : EditHabitEvent()

    object SuccessDeleteHabit : EditHabitEvent()
}
