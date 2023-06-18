package com.bibbidi.habittracker.ui.updatehabit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bibbidi.habittracker.ui.common.MutableEventFlow
import com.bibbidi.habittracker.ui.common.asEventFlow
import com.bibbidi.habittracker.ui.model.habit.habitinfo.CheckHabitInfoUiModel
import com.bibbidi.habittracker.ui.model.habit.habitinfo.HabitInfoUiModel
import com.bibbidi.habittracker.ui.model.habit.habitinfo.TimeHabitInfoUiModel
import com.bibbidi.habittracker.ui.model.habit.habitinfo.TrackHabitInfoUiModel
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

class UpdateHabitViewModel @AssistedInject constructor(@Assisted private val habitInfo: HabitInfoUiModel) :
    ViewModel() {

    companion object {
        fun provideFactory(
            assistedFactory: HabitInfoAssistedFactory,
            habitInfo: HabitInfoUiModel
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(habitInfo) as T
            }
        }
    }

    @AssistedFactory
    interface HabitInfoAssistedFactory {
        fun create(habitInfo: HabitInfoUiModel): UpdateHabitViewModel
    }

    val nameFlow = MutableStateFlow(habitInfo.name)
    val emojiFlow = MutableStateFlow(habitInfo.emoji)
    val whenRunFlow = MutableStateFlow(habitInfo.whenRun)
    val alarmTimeFlow = MutableStateFlow(habitInfo.alarmTime)

    private val _event = MutableEventFlow<UpdateHabitEvent>()
    val event = _event.asEventFlow()

    val isEnabled: StateFlow<Boolean> = combine(
        nameFlow,
        emojiFlow,
        whenRunFlow
    ) { name, emoji, whenRun ->
        (name.isNotEmpty()) && (emoji.isNotEmpty()) && whenRun.isNotEmpty()
    }.stateIn(viewModelScope, SharingStarted.Eagerly, false)

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

    fun setWhenRun(whenRunString: String) {
        viewModelScope.launch {
            whenRunFlow.value = whenRunString
        }
    }

    fun showInputEmojiDialog() {
        viewModelScope.launch {
            _event.emit(UpdateHabitEvent.EmojiClickedEvent)
        }
    }

    fun showInputWhenRunDialog() {
        viewModelScope.launch {
            _event.emit(UpdateHabitEvent.WhenRunClickedEvent)
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

    fun onSubmit() {
        viewModelScope.launch {
            val habitInfo = when (habitInfo) {
                is CheckHabitInfoUiModel -> habitInfo.copy(
                    name = nameFlow.value,
                    emoji = emojiFlow.value,
                    alarmTime = alarmTimeFlow.value
                )
                is TimeHabitInfoUiModel -> habitInfo.copy(
                    name = nameFlow.value,
                    emoji = emojiFlow.value,
                    alarmTime = alarmTimeFlow.value
                )
                is TrackHabitInfoUiModel -> habitInfo.copy(
                    name = nameFlow.value,
                    emoji = emojiFlow.value,
                    alarmTime = alarmTimeFlow.value
                )
            }
            _event.emit(UpdateHabitEvent.SubmitEvent(habitInfo))
        }
    }
}
