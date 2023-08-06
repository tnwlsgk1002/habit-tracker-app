package com.bibbidi.habittracker.ui.sethabit.updatehabit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bibbidi.habittracker.ui.common.MutableEventFlow
import com.bibbidi.habittracker.ui.common.asEventFlow
import com.bibbidi.habittracker.ui.model.TimeFilterUiModel
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
import org.threeten.bp.LocalTime

class UpdateHabitViewModel @AssistedInject constructor(
    @Assisted private val habit: HabitUiModel
) :
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
        fun create(habitInfo: HabitUiModel): UpdateHabitViewModel
    }

    val nameFlow = MutableStateFlow(habit.name)
    val emojiFlow = MutableStateFlow(habit.emoji)
    val alarmTimeFlow = MutableStateFlow(habit.alarmTime)

    val morningFilterChecked =
        MutableStateFlow(habit.timeFilters.contains(TimeFilterUiModel.MORNING))
    val afternoonFilterChecked =
        MutableStateFlow(habit.timeFilters.contains(TimeFilterUiModel.AFTERNOON))
    val eveningFilterChecked =
        MutableStateFlow(habit.timeFilters.contains(TimeFilterUiModel.EVENING))

    private val timeFiltersFlow =
        combine(morningFilterChecked, afternoonFilterChecked, eveningFilterChecked) { morning, afternoon, evening ->
            setOfNotNull(
                if (morning) TimeFilterUiModel.MORNING else null,
                if (afternoon) TimeFilterUiModel.AFTERNOON else null,
                if (evening) TimeFilterUiModel.EVENING else null
            )
        }.stateIn(viewModelScope, SharingStarted.Eagerly, habit.timeFilters)

    val morningTimeFilterEnabledFlow =
        combine(afternoonFilterChecked, eveningFilterChecked) { afternoon, evening ->
            afternoon || evening
        }.stateIn(viewModelScope, SharingStarted.Eagerly, true)

    val afternoonTimeFilterEnabledFlow =
        combine(morningFilterChecked, eveningFilterChecked) { morning, evening ->
            morning || evening
        }.stateIn(viewModelScope, SharingStarted.Eagerly, true)

    val eveningTimeFilterEnabledFlow =
        combine(morningFilterChecked, afternoonFilterChecked) { morning, afternoon ->
            morning || afternoon
        }.stateIn(viewModelScope, SharingStarted.Eagerly, true)

    private val _event = MutableEventFlow<UpdateHabitEvent>()
    val event = _event.asEventFlow()

    val isEnabled: StateFlow<Boolean> = combine(
        nameFlow,
        emojiFlow,
        timeFiltersFlow
    ) { name, emoji, timeFilters ->
        name.isNotEmpty() && emoji.isNotEmpty() && timeFilters.isNotEmpty()
    }
        .stateIn(viewModelScope, SharingStarted.Eagerly, false)

    fun setEmoji(emoji: String) {
        viewModelScope.launch {
            emojiFlow.value = emoji
        }
    }

    fun setAlarmTime(alarmTime: LocalTime) {
        viewModelScope.launch {
            alarmTimeFlow.value = alarmTime
        }
    }

    fun showInputEmojiDialog() {
        viewModelScope.launch {
            _event.emit(UpdateHabitEvent.EmojiClickedEvent)
        }
    }

    fun showInputAlarmDialog() {
        viewModelScope.launch {
            if (alarmTimeFlow.value == null) {
                _event.emit(UpdateHabitEvent.AlarmTimeClickedEvent)
            } else {
                alarmTimeFlow.emit(null)
            }
        }
    }

    fun showLeastOneSelectedTimeFilter() {
        viewModelScope.launch {
            _event.emit(UpdateHabitEvent.ShowLeastOneSelectedTimeFilterEvent)
        }
    }

    fun submit() {
        viewModelScope.launch {
            val updatedHabit = habit.copy(
                name = nameFlow.value,
                emoji = emojiFlow.value,
                alarmTime = alarmTimeFlow.value,
                timeFilters = timeFiltersFlow.value
            )
            _event.emit(UpdateHabitEvent.SubmitEvent(updatedHabit))
        }
    }
}
