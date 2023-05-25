package com.bibbidi.habittracker.ui.addhabit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bibbidi.habittracker.domain.HabitsRepository
import com.bibbidi.habittracker.ui.model.DayOfTheWeekUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import org.threeten.bp.Duration
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import javax.inject.Inject

@HiltViewModel
class SetHabitViewModel @Inject constructor(
    private val habitsRepository: HabitsRepository
) : ViewModel() {

    companion object {
        const val DEFAULT_NAME = ""
        const val DEFAULT_EMOJI = "✅"
        val DEFAULT_ALARM_TIME = null
        const val DEFAULT_WHEN_RUN = ""
        val DEFAULT_REPEATS_DAY_OF_THE_WEEKS = DayOfTheWeekUiModel.values().toSet()
        val DEFAULT_GOAL_TIME: Duration = Duration.ofHours(1L)
        val DEFAULT_START_DATE: LocalDate
            get() = LocalDate.now()
    }

    val name: MutableLiveData<String> by lazy {
        MutableLiveData(DEFAULT_NAME)
    }

    val emoji: MutableLiveData<String> by lazy {
        MutableLiveData(DEFAULT_EMOJI)
    }

    val alarmTime: MutableLiveData<LocalTime?> by lazy {
        MutableLiveData(DEFAULT_ALARM_TIME)
    }

    val whenRun: MutableLiveData<String> by lazy {
        MutableLiveData(DEFAULT_WHEN_RUN)
    }

    val repeatsDayOfTheWeeks: MutableLiveData<Set<DayOfTheWeekUiModel>> by lazy {
        MutableLiveData(DEFAULT_REPEATS_DAY_OF_THE_WEEKS)
    }

    val goalTime: MutableLiveData<Duration> by lazy {
        MutableLiveData(DEFAULT_GOAL_TIME)
    }

    val startDate: MutableLiveData<LocalDate> by lazy {
        MutableLiveData(DEFAULT_START_DATE)
    }

    fun setHabit() {
        // TODO("데이터 확인 후 repository 에 접근하여 데이터 insert")
    }
}
