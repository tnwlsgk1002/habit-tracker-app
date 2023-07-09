package com.bibbidi.habittracker.ui.addhabit

import com.bibbidi.habittracker.ui.model.habit.habitinfo.HabitInfoUiModel

sealed class AddHabitEvent {

    object EmojiClickedEvent : AddHabitEvent()

    object AlarmTimeClickedEvent : AddHabitEvent()

    object WhenRunClickedEvent : AddHabitEvent()

    object RepeatsDayOfTheWeekClickEvent : AddHabitEvent()

    object StartDateClickEvent : AddHabitEvent()

    data class SubmitEvent(val habitInfo: HabitInfoUiModel) : AddHabitEvent()

    object StartDateIsBeforeNowEvent : AddHabitEvent()
}
