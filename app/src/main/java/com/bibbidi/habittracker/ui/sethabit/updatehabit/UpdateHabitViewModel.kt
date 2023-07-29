package com.bibbidi.habittracker.ui.sethabit.updatehabit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bibbidi.habittracker.data.source.HabitsRepository
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
import org.threeten.bp.LocalTime

class UpdateHabitViewModel @AssistedInject constructor(
    private val repository: HabitsRepository,
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

    private val _event = MutableEventFlow<UpdateHabitEvent>()
    val event = _event.asEventFlow()

    val isEnabled: StateFlow<Boolean> = combine(
        nameFlow,
        emojiFlow
    ) { name, emoji ->
        (name.isNotEmpty()) && (emoji.isNotEmpty())
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

    fun submit() {
        viewModelScope.launch {
            val updatedHabit = habit.copy(
                name = nameFlow.value,
                emoji = emojiFlow.value,
                alarmTime = alarmTimeFlow.value
            )
            _event.emit(UpdateHabitEvent.SubmitEvent(updatedHabit))
        }
    }
}
