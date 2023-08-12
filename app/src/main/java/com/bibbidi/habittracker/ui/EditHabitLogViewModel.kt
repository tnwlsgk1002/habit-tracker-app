package com.bibbidi.habittracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibbidi.habittracker.domain.usecase.habitmemo.DeleteHabitMemoUseCase
import com.bibbidi.habittracker.domain.usecase.habitmemo.SaveHabitMemoUseCase
import com.bibbidi.habittracker.domain.usecase.habltlog.CompleteHabitUseCase
import com.bibbidi.habittracker.ui.mapper.asDomain
import com.bibbidi.habittracker.ui.model.habit.HabitMemoItem
import com.bibbidi.habittracker.ui.model.habit.HabitWithLogUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditHabitLogViewModel @Inject constructor(
    private val saveHabitMemoUseCase: SaveHabitMemoUseCase,
    private val deleteHabitMemoUseCase: DeleteHabitMemoUseCase,
    private val completeHabitUseCase: CompleteHabitUseCase
) : ViewModel() {

    fun checkHabitLog(log: HabitWithLogUiModel, isChecked: Boolean) {
        viewModelScope.launch {
            completeHabitUseCase(log.asDomain(), isChecked)
        }
    }

    fun saveHabitMemo(habitWithLog: HabitWithLogUiModel, memo: String?) {
        viewModelScope.launch {
            saveHabitMemoUseCase(habitWithLog.habitLog.asDomain(), memo)
        }
    }

    fun saveHabitMemo(memoItem: HabitMemoItem, memo: String?) {
        viewModelScope.launch {
            saveHabitMemoUseCase(memoItem.asDomain(), memo)
        }
    }

    fun deleteHabitMemo(habitWithLog: HabitWithLogUiModel) {
        viewModelScope.launch {
            deleteHabitMemoUseCase(habitWithLog.habitLog.asDomain())
        }
    }

    fun deleteHabitMemo(memoItem: HabitMemoItem) {
        viewModelScope.launch {
            deleteHabitMemoUseCase(memoItem.asDomain())
        }
    }
}
