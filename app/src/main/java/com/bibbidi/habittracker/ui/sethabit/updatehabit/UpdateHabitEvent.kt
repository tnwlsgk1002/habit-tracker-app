package com.bibbidi.habittracker.ui.sethabit.updatehabit

import com.bibbidi.habittracker.ui.model.habit.HabitUiModel

sealed class UpdateHabitEvent {

    object EmojiClickedEvent : UpdateHabitEvent()

    object AlarmTimeClickedEvent : UpdateHabitEvent()

    data class SubmitEvent(val habitInfo: HabitUiModel) : UpdateHabitEvent()
}
