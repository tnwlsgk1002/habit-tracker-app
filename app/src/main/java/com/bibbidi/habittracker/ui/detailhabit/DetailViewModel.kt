package com.bibbidi.habittracker.ui.detailhabit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibbidi.habittracker.data.model.DBResult
import com.bibbidi.habittracker.data.source.HabitsRepository
import com.bibbidi.habittracker.ui.common.MutableEventFlow
import com.bibbidi.habittracker.ui.common.UiState
import com.bibbidi.habittracker.ui.common.asEventFlow
import com.bibbidi.habittracker.ui.mapper.asUiModel
import com.bibbidi.habittracker.ui.model.habit.HabitResultUiModel
import com.bibbidi.habittracker.ui.model.habit.HabitUiModel
import com.bibbidi.habittracker.ui.model.habit.HabitWithLogUiModel
import com.bibbidi.habittracker.ui.model.habit.HabitWithLogsUiModel
import com.bibbidi.habittracker.utils.isSameYearAndMonth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: HabitsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object {
        const val HABIT_WITH_LOG_KEY = "habit_with_log"
    }

    private val id = savedStateHandle.get<HabitWithLogUiModel>(HABIT_WITH_LOG_KEY)?.habit?.id

    private val dateFlow: MutableStateFlow<LocalDate>

    private val _habitFlow = MutableStateFlow(HabitUiModel())
    val habitFlow = _habitFlow.asStateFlow()

    private val _habitResultFlow =
        MutableStateFlow<UiState<HabitResultUiModel>>(UiState.Loading)
    val habitResultFlow = _habitResultFlow.asStateFlow()

    private val _habitWithLogsFlow =
        MutableStateFlow<UiState<HabitWithLogsUiModel>>(UiState.Loading)
    val habitWithLogsFlow = _habitWithLogsFlow.asStateFlow()

    private val _event = MutableEventFlow<DetailHabitEvent>()
    val event = _event.asEventFlow()

    init {
        val now = LocalDate.now()
        dateFlow = MutableStateFlow(now)
        fetchHabit()
        loadHabitWithLogs()
        loadHabitResult(now)
    }

    val memoFlow = combine(dateFlow, habitWithLogsFlow) { date, habitWithLogs ->
        when (habitWithLogs) {
            is UiState.Success -> UiState.Success(
                habitWithLogs.data.habitLogs.filter { (d, l) ->
                    d.isSameYearAndMonth(date) && l.memo != null
                }.values.toList()
            )
            is UiState.Loading -> UiState.Loading
            else -> UiState.Empty
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, UiState.Loading)

    fun fetchHabit() {
        viewModelScope.launch {
            _habitFlow.value = repository.getHabitById(id).asUiModel()
        }
    }

    private fun loadHabitWithLogs() {
        viewModelScope.launch {
            repository.getHabitWithLogs(id).collectLatest {
                _habitWithLogsFlow.value = when (it) {
                    is DBResult.Success -> UiState.Success(it.data.asUiModel())
                    is DBResult.Loading -> UiState.Loading
                    else -> UiState.Empty
                }
            }
        }
    }

    private fun loadHabitResult(date: LocalDate) {
        viewModelScope.launch {
            repository.getHabitResult(id, date).collectLatest {
                _habitResultFlow.value = when (it) {
                    is DBResult.Success -> UiState.Success(it.data.asUiModel())
                    is DBResult.Loading -> UiState.Loading
                    else -> UiState.Empty
                }
            }
        }
    }

    val setDate: (LocalDate) -> Unit = {
        viewModelScope.launch {
            dateFlow.value = it
        }
    }

    fun showDeleteHabit() {
        viewModelScope.launch {
            _event.emit(DetailHabitEvent.ShowDeleteHabit(habitFlow.value))
        }
    }

    fun showUpdateHabit() {
        viewModelScope.launch {
            _event.emit(DetailHabitEvent.ShowUpdateHabit(habitFlow.value))
        }
    }
}
