package com.bibbidi.habittracker.ui.addhabit.check

import com.bibbidi.habittracker.ui.addhabit.AddHabitViewModel
import com.bibbidi.habittracker.ui.model.habit.habitinfo.CheckHabitInfoUiModel
import com.bibbidi.habittracker.ui.model.habit.habitinfo.HabitInfoUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddCheckHabitViewModel @Inject constructor() : AddHabitViewModel() {

    override val cntHabitInfo: HabitInfoUiModel
        get() = CheckHabitInfoUiModel(
            habitId = null,
            childId = null,
            name = nameFlow.value,
            emoji = emojiFlow.value,
            alarmTime = alarmTimeFlow.value,
            whenRun = whenRunFlow.value,
            repeatsDayOfTheWeeks = repeatsDayOfTheWeeksFlow.value,
            startDate = startDateFlow.value
        )
}
