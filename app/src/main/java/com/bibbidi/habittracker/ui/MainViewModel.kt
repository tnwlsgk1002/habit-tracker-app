package com.bibbidi.habittracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibbidi.habittracker.data.source.HabitsRepository
import com.bibbidi.habittracker.ui.common.MutableEventFlow
import com.bibbidi.habittracker.ui.common.asEventFlow
import com.bibbidi.habittracker.ui.mapper.asDomain
import com.bibbidi.habittracker.ui.model.habit.HabitUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: HabitsRepository
) : ViewModel() {
    private val _event: MutableEventFlow<MainEvent> = MutableEventFlow()
    val event = _event.asEventFlow()

    fun addHabit(habit: HabitUiModel) {
        viewModelScope.launch {
            repository.insertHabit(habit.asDomain())
            _event.emit(MainEvent.SuccessAddHabit)
        }
    }

    fun deleteHabit(habit: HabitUiModel) {
        viewModelScope.launch {
            habit.id?.let {
                repository.deleteHabitById(it)
                _event.emit(MainEvent.SuccessDeleteHabit)
            }
        }
    }

    fun updateHabit(habit: HabitUiModel) {
        viewModelScope.launch {
            repository.updateHabit(habit.asDomain())
            _event.emit(MainEvent.SuccessUpdateHabit)
        }
    }
}
