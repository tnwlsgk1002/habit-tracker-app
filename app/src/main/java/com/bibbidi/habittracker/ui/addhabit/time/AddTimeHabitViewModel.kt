package com.bibbidi.habittracker.ui.addhabit.time

import androidx.lifecycle.viewModelScope
import com.bibbidi.habittracker.ui.addhabit.AddHabitViewModel
import com.bibbidi.habittracker.ui.common.MutableEventFlow
import com.bibbidi.habittracker.ui.model.habit.habitinfo.TimeHabitInfoUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.threeten.bp.Duration
import javax.inject.Inject

@HiltViewModel
class AddTimeHabitViewModel @Inject constructor() : AddHabitViewModel() {

    val goalTimeFlow = MutableStateFlow(Duration.ofHours(1L))
    val goalTimeClickEvent = MutableEventFlow<Duration>()

    fun showInputGoalTimeDialog() {
        viewModelScope.launch {
            goalTimeClickEvent.emit(goalTimeFlow.value)
        }
    }

    fun setGoalTime(duration: Duration) {
        viewModelScope.launch {
            goalTimeFlow.value = duration
        }
    }

    override fun setHabitEvent() {
        viewModelScope.launch {
            submitEvent.emit(
                TimeHabitInfoUiModel(
                    habitId = null,
                    childId = null,
                    name = nameFlow.value,
                    emoji = emojiFlow.value,
                    alarmTime = alarmTimeFlow.value,
                    whenRun = whenRunFlow.value,
                    repeatsDayOfTheWeeks = repeatsDayOfTheWeeksFlow.value,
                    startDate = startDateFlow.value,
                    goalDuration = goalTimeFlow.value
                )
            )
        }
    }
}
