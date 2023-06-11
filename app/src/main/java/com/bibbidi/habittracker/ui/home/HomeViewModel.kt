package com.bibbidi.habittracker.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibbidi.habittracker.domain.HabitsRepository
import com.bibbidi.habittracker.domain.model.DBResult
import com.bibbidi.habittracker.ui.common.EventFlow
import com.bibbidi.habittracker.ui.common.MutableEventFlow
import com.bibbidi.habittracker.ui.common.UiState
import com.bibbidi.habittracker.ui.common.asEventFlow
import com.bibbidi.habittracker.ui.mapper.habitinfo.asDomain
import com.bibbidi.habittracker.ui.mapper.habitlog.asUiModel
import com.bibbidi.habittracker.ui.model.date.DateItem
import com.bibbidi.habittracker.ui.model.habit.habitinfo.HabitInfoUiModel
import com.bibbidi.habittracker.ui.model.habit.log.HabitLogUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val habitsRepository: HabitsRepository
) : ViewModel() {

    private val _dateFlow: MutableStateFlow<LocalDate> = MutableStateFlow(LocalDate.now())
    val dateFlow: StateFlow<LocalDate> = _dateFlow.asStateFlow()

    private val _dateItemsFlow: MutableStateFlow<List<UiState<Array<DateItem>>>> = MutableStateFlow(
        List(3) { UiState.Loading }
    )
    val dateItemsFlow: StateFlow<List<UiState<Array<DateItem>>>> = _dateItemsFlow.asStateFlow()

    private val _habitsFlow: MutableStateFlow<UiState<List<HabitLogUiModel>>> =
        MutableStateFlow(UiState.Loading)
    val habitsFlow: StateFlow<UiState<List<HabitLogUiModel>>> = _habitsFlow.asStateFlow()

    private val _event: MutableEventFlow<HomeEvent> = MutableEventFlow()
    val event: EventFlow<HomeEvent> = _event.asEventFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            dateFlow.collectLatest { date ->
                habitsRepository.getHabitAndHabitLogsByDate(date).collectLatest { result ->
                    _habitsFlow.value = when (result) {
                        is DBResult.Success -> UiState.Success(result.data.map { it.asUiModel() })
                        is DBResult.Loading -> UiState.Loading
                        else -> return@collectLatest
                    }
                }
            }
        }
    }

    fun setDate(date: LocalDate) {
        viewModelScope.launch {
            _dateFlow.value = date
        }
    }

    fun clickDateIcon() {
        viewModelScope.launch {
            _event.emit(HomeEvent.ShowDatePicker(date = _dateFlow.value))
        }
    }

    fun clickFab() {
        viewModelScope.launch {
            _event.emit(HomeEvent.ShowSelectHabitType)
        }
    }

    fun setHabit(habitInfo: HabitInfoUiModel) {
        viewModelScope.launch {
            habitsRepository.insertHabit(habitInfo.asDomain())
            _event.emit(HomeEvent.SuccessAddHabit)
        }
    }
}
