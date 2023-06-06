package com.bibbidi.habittracker.ui.home

sealed class HomeMessageEvent {
    object SuccessAddHabit : HomeMessageEvent()
}
