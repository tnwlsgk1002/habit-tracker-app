package com.bibbidi.habittracker.ui.addhabit.track

import com.bibbidi.habittracker.ui.addhabit.AddHabitViewModel
import com.bibbidi.habittracker.ui.model.habit.habitinfo.HabitInfoUiModel
import com.bibbidi.habittracker.ui.model.habit.habitinfo.TrackHabitInfoUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddTrackHabitViewModel @Inject constructor() : AddHabitViewModel() {

    override val cntHabitInfo: HabitInfoUiModel
        get() = TrackHabitInfoUiModel(
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
