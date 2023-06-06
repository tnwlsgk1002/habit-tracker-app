package com.bibbidi.habittracker.ui.addhabit.check

import androidx.lifecycle.viewModelScope
import com.bibbidi.habittracker.ui.addhabit.AddHabitViewModel
import com.bibbidi.habittracker.ui.model.habit.habitinfo.CheckHabitInfoUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCheckHabitViewModel @Inject constructor() : AddHabitViewModel() {

    override fun setHabitEvent() {
        viewModelScope.launch {
            submitEvent.emit(
                CheckHabitInfoUiModel(
                    habitId = null,
                    childId = null,
                    name = nameFlow.value,
                    emoji = emojiFlow.value,
                    alarmTime = alarmTimeFlow.value,
                    whenRun = whenRunFlow.value,
                    repeatsDayOfTheWeeks = repeatsDayOfTheWeeksFlow.value,
                    startDate = startDateFlow.value
                )
            )
        }
    }
}
