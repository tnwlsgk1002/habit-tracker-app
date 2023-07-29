package com.bibbidi.habittracker.ui.sethabit.addhabit

import com.bibbidi.habittracker.ui.model.habit.HabitUiModel

sealed class AddHabitEvent {

    object EmojiClickedEvent : AddHabitEvent()

    object AlarmTimeClickedEvent : AddHabitEvent()

    object RepeatsDayOfTheWeekClickEvent : AddHabitEvent()

    object StartDateClickEvent : AddHabitEvent()

    object StartDateIsBeforeNowEvent : AddHabitEvent()

    data class SubmitEvent(val habit: HabitUiModel) : AddHabitEvent()
}
