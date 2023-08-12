package com.bibbidi.habittracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibbidi.habittracker.domain.usecase.habit.AddHabitUseCase
import com.bibbidi.habittracker.domain.usecase.habit.DeleteHabitUseCase
import com.bibbidi.habittracker.domain.usecase.habit.UpdateHabitUseCase
import com.bibbidi.habittracker.ui.common.MutableEventFlow
import com.bibbidi.habittracker.ui.common.asEventFlow
import com.bibbidi.habittracker.ui.mapper.asDomain
import com.bibbidi.habittracker.ui.model.habit.HabitUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditHabitViewModel @Inject constructor(
    private val addHabitUseCase: AddHabitUseCase,
    private val deleteHabitUseCase: DeleteHabitUseCase,
    private val updateHabitUseCase: UpdateHabitUseCase
) : ViewModel() {

    private val _event: MutableEventFlow<EditHabitEvent> = MutableEventFlow()
    val event = _event.asEventFlow()

    fun addHabit(habit: HabitUiModel) {
        viewModelScope.launch {
            addHabitUseCase(habit.asDomain())
            _event.emit(EditHabitEvent.SuccessAddHabit)
        }
    }

    fun deleteHabit(habit: HabitUiModel) {
        viewModelScope.launch {
            deleteHabitUseCase(habit.id)
            _event.emit(EditHabitEvent.SuccessDeleteHabit)
        }
    }

    fun updateHabit(habit: HabitUiModel) {
        viewModelScope.launch {
            updateHabitUseCase(habit.asDomain())
            _event.emit(EditHabitEvent.SuccessUpdateHabit)
        }
    }
}
