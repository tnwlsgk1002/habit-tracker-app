package com.bibbidi.habittracker.ui.addhabit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibbidi.habittracker.ui.common.MutableEventFlow
import com.bibbidi.habittracker.ui.model.habit.habitinfo.HabitInfoUiModel
import com.bibbidi.habittracker.utils.dayOfWeekValues
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

abstract class AddHabitViewModel : ViewModel() {

    val nameFlow = MutableStateFlow("")
    val emojiFlow = MutableStateFlow("")
    val alarmTimeFlow = MutableStateFlow<LocalTime?>(null)
    val whenRunFlow = MutableStateFlow("")
    val repeatsDayOfTheWeeksFlow = MutableStateFlow(dayOfWeekValues.toSet())
    val startDateFlow = MutableStateFlow(LocalDate.now())

    val submitEvent = MutableEventFlow<HabitInfoUiModel>()
    val emojiClickEvent = MutableEventFlow<String>()
    val alarmClickEvent = MutableEventFlow<LocalTime?>()
    val whenRunClickEvent = MutableEventFlow<String>()
    val repeatsDayOfTheWeeksClickEvent = MutableEventFlow<Set<DayOfWeek>>()
    val startDateClickEvent = MutableEventFlow<LocalDate>()

    val messageEvent = MutableEventFlow<AddHabitMessageEvent>()

    open val isEnabled: StateFlow<Boolean> = combine(
        nameFlow,
        emojiFlow,
        whenRunFlow,
        repeatsDayOfTheWeeksFlow
    ) { name, emoji, whenRun, repeatsDayOfTheWeeks ->
        (name.isNotEmpty()) && (emoji.isNotEmpty()) && (whenRun.isNotEmpty()) && (repeatsDayOfTheWeeks.isNotEmpty())
    }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    open val isValid: Boolean
        get() = !startDateFlow.value.isBefore(LocalDate.now())

    abstract val cntHabitInfo: HabitInfoUiModel

    fun setEmoji(emojiString: String) {
        viewModelScope.launch {
            emojiFlow.value = emojiString
        }
    }

    fun setAlarmTime(alarmTime: LocalTime?) {
        viewModelScope.launch {
            alarmTimeFlow.value = alarmTime
        }
    }

    fun setWhenRun(whenRunString: String) {
        viewModelScope.launch {
            whenRunFlow.value = whenRunString
        }
    }

    fun setRepeatsDayOfTheWeeks(repeatDayOfWeeks: Set<DayOfWeek>) {
        viewModelScope.launch {
            repeatsDayOfTheWeeksFlow.value = repeatDayOfWeeks
        }
    }

    fun setStartDate(startDate: LocalDate) {
        viewModelScope.launch {
            startDateFlow.value = startDate
        }
    }

    fun showInputEmojiDialog() {
        viewModelScope.launch {
            emojiClickEvent.emit(emojiFlow.value)
        }
    }

    fun showInputAlarmDialog(isChecked: Boolean) {
        viewModelScope.launch {
            if (isChecked) {
                alarmClickEvent.emit(alarmTimeFlow.value)
            } else {
                alarmTimeFlow.emit(null)
            }
        }
    }

    fun showInputWhenRunDialog() {
        viewModelScope.launch {
            whenRunClickEvent.emit(whenRunFlow.value)
        }
    }

    fun showInputRepeatsDayOfTheWeeksDialog() {
        viewModelScope.launch {
            repeatsDayOfTheWeeksClickEvent.emit(repeatsDayOfTheWeeksFlow.value)
        }
    }

    fun showInputStartDateDialog() {
        viewModelScope.launch {
            startDateClickEvent.emit(startDateFlow.value)
        }
    }

    fun addHabit() {
        viewModelScope.launch {
            if (isValid) {
                submitEvent.emit(cntHabitInfo)
            } else {
                messageEvent.emit(AddHabitMessageEvent.StartDateIsBeforeNowEvent)
            }
        }
    }
}
