package com.bibbidi.habittracker.ui.addhabit

sealed class AddHabitMessageEvent {

    object StartDateIsBeforeNowEvent : AddHabitMessageEvent()
}
