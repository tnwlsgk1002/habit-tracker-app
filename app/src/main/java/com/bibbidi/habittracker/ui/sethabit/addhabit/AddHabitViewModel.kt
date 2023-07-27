package com.bibbidi.habittracker.ui.sethabit.addhabit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bibbidi.habittracker.ui.common.MutableEventFlow
import com.bibbidi.habittracker.ui.common.asEventFlow
import com.bibbidi.habittracker.ui.model.habit.HabitUiModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

class AddHabitViewModel @AssistedInject constructor(@Assisted habit: HabitUiModel) :
    ViewModel() {

    companion object {
        fun provideFactory(
            assistedFactory: HabitInfoAssistedFactory,
            habitInfo: HabitUiModel
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(habitInfo) as T
            }
        }
    }

    @AssistedFactory
    interface HabitInfoAssistedFactory {
        fun create(habit: HabitUiModel): AddHabitViewModel
    }

    val nameFlow = MutableStateFlow(habit.name)
    val emojiFlow = MutableStateFlow(habit.emoji)
    val alarmTimeFlow = MutableStateFlow(habit.alarmTime)
    val repeatsDayOfTheWeeksFlow = MutableStateFlow(habit.repeatsDayOfTheWeeks)
    val startDateFlow = MutableStateFlow(habit.startDate)

    private val _event = MutableEventFlow<AddHabitEvent>()
    val event = _event.asEventFlow()

    val isEnabled: StateFlow<Boolean> = combine(
        nameFlow,
        emojiFlow,
        repeatsDayOfTheWeeksFlow
    ) { name, emoji, repeatsDayOfTheWeeks ->
        (name.isNotEmpty()) && (emoji.isNotEmpty()) && (repeatsDayOfTheWeeks.isNotEmpty())
    }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

    private val isValid: Boolean
        get() = !startDateFlow.value.isBefore(LocalDate.now())

    private val habit
        get() = HabitUiModel(
            name = nameFlow.value,
            emoji = emojiFlow.value,
            alarmTime = alarmTimeFlow.value,
            repeatsDayOfTheWeeks = repeatsDayOfTheWeeksFlow.value,
            startDate = startDateFlow.value
        )

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
            _event.emit(AddHabitEvent.EmojiClickedEvent)
        }
    }

    fun showInputAlarmDialog() {
        viewModelScope.launch {
            if (alarmTimeFlow.value == null) {
                _event.emit(AddHabitEvent.AlarmTimeClickedEvent)
            } else {
                alarmTimeFlow.emit(null)
            }
        }
    }

    fun showInputRepeatsDayOfTheWeeksDialog() {
        viewModelScope.launch {
            _event.emit(AddHabitEvent.RepeatsDayOfTheWeekClickEvent)
        }
    }

    fun showInputStartDateDialog() {
        viewModelScope.launch {
            _event.emit(AddHabitEvent.StartDateClickEvent)
        }
    }

    fun addHabit() {
        viewModelScope.launch {
            if (isValid) {
                _event.emit(AddHabitEvent.SubmitEvent(habit))
            } else {
                _event.emit(AddHabitEvent.StartDateIsBeforeNowEvent)
            }
        }
    }
}
