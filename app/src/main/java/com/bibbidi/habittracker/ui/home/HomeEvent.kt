package com.bibbidi.habittracker.ui.home

import com.bibbidi.habittracker.ui.model.habit.HabitAlarmUiModel
import com.bibbidi.habittracker.ui.model.habit.habitinfo.HabitInfoUiModel
import com.bibbidi.habittracker.ui.model.habit.log.HabitLogUiModel
import com.bibbidi.habittracker.ui.model.habit.log.TrackHabitLogUiModel
import org.threeten.bp.LocalDate

sealed class HomeEvent {

    object ShowSelectHabitType : HomeEvent()

    data class SuccessAddHabit(val alarmInfo: HabitAlarmUiModel) : HomeEvent()

    data class SuccessUpdateHabit(val alarmInfo: HabitAlarmUiModel) : HomeEvent()

    data class SuccessDeleteHabit(val alarmInfo: HabitAlarmUiModel) : HomeEvent()

    data class ShowDatePicker(val date: LocalDate) : HomeEvent()

    data class ShowTrackValueDialog(val habitLog: TrackHabitLogUiModel) : HomeEvent()

    data class AttemptDeleteHabit(val habitLog: HabitLogUiModel) : HomeEvent()

    data class AttemptUpdateHabit(val habitInfo: HabitInfoUiModel) : HomeEvent()
}
