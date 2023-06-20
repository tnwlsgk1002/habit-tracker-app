package com.bibbidi.habittracker.ui.updatehabit

import com.bibbidi.habittracker.ui.model.habit.habitinfo.HabitInfoUiModel

sealed class UpdateHabitEvent {

    object EmojiClickedEvent : UpdateHabitEvent()

    object AlarmTimeClickedEvent : UpdateHabitEvent()

    object WhenRunClickedEvent : UpdateHabitEvent()

    data class SubmitEvent(val habitInfo: HabitInfoUiModel) : UpdateHabitEvent()
}
