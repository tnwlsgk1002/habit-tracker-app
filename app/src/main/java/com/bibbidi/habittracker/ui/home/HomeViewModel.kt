package com.bibbidi.habittracker.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibbidi.habittracker.data.model.DBResult
import com.bibbidi.habittracker.domain.usecase.habit.GetHabitUseCase
import com.bibbidi.habittracker.domain.usecase.habitmemo.DeleteHabitMemoUseCase
import com.bibbidi.habittracker.domain.usecase.habitmemo.SaveHabitMemoUseCase
import com.bibbidi.habittracker.domain.usecase.habitresult.GetHabitProgressByDateUseCase
import com.bibbidi.habittracker.domain.usecase.habltlog.CompleteHabitUseCase
import com.bibbidi.habittracker.domain.usecase.habltlog.GetHabitLogsByDateUseCase
import com.bibbidi.habittracker.ui.common.Constants.ONE_WEEK
import com.bibbidi.habittracker.ui.common.Constants.ROW_CALENDAR_SIZE
import com.bibbidi.habittracker.ui.common.EventFlow
import com.bibbidi.habittracker.ui.common.MutableEventFlow
import com.bibbidi.habittracker.ui.common.UiState
import com.bibbidi.habittracker.ui.common.asEventFlow
import com.bibbidi.habittracker.ui.mapper.asDomain
import com.bibbidi.habittracker.ui.mapper.asUiModel
import com.bibbidi.habittracker.ui.model.ProgressUiModel
import com.bibbidi.habittracker.ui.model.date.DateItem
import com.bibbidi.habittracker.ui.model.date.getDateItemsByDate
import com.bibbidi.habittracker.ui.model.habit.HabitUiModel
import com.bibbidi.habittracker.ui.model.habit.HabitWithLogUiModel
import com.bibbidi.habittracker.utils.getStartOfTheWeek
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHabitUseCase: GetHabitUseCase,
    private val getHabitLogsByDateUseCase: GetHabitLogsByDateUseCase,
    private val getHabitProgressByDateUseCase: GetHabitProgressByDateUseCase,
    private val completeHabitUseCase: CompleteHabitUseCase,
    private val saveHabitMemoUseCase: SaveHabitMemoUseCase,
    private val deleteHabitMemoUseCase: DeleteHabitMemoUseCase
) : ViewModel() {

    companion object {
        const val DATE_ITEM_DELAY = 500L
    }

    private val _dateFlow: MutableStateFlow<LocalDate> = MutableStateFlow(LocalDate.now())
    val dateFlow: StateFlow<LocalDate> = _dateFlow.asStateFlow()

    private val _dateItemsFlow: MutableStateFlow<List<UiState<Array<DateItem>>>> =
        MutableStateFlow(List(ROW_CALENDAR_SIZE) { UiState.Loading })
    val dateItemsFlow: StateFlow<List<UiState<Array<DateItem>>>> = _dateItemsFlow.asStateFlow()

    private val _progressFlow = MutableStateFlow<UiState<ProgressUiModel>>(UiState.Loading)
    val progressFlow = _progressFlow.asStateFlow()

    private val _habitLogsFlow = MutableStateFlow<UiState<List<HabitWithLogUiModel>>>(UiState.Loading)
    val habitLogsFlow = _habitLogsFlow.asStateFlow()

    private val _event: MutableEventFlow<HomeEvent> = MutableEventFlow()
    val event: EventFlow<HomeEvent> = _event.asEventFlow()

    init {
        setDateItemsFlow(dateFlow.value)
        loadHabitLogs()
        loadHabitProgress()
    }

    private fun loadHabitLogs() {
        viewModelScope.launch {
            dateFlow.collectLatest { date ->
                getHabitLogsByDateUseCase(date).collectLatest {
                    _habitLogsFlow.value = when (it) {
                        is DBResult.Success -> UiState.Success(it.data.map { it.asUiModel() })
                        is DBResult.Loading -> UiState.Loading
                        is DBResult.Empty -> UiState.Empty
                        else -> UiState.Empty
                    }
                }
            }
        }
    }

    private fun loadHabitProgress() {
        viewModelScope.launch {
            dateFlow.collectLatest { date ->
                getHabitProgressByDateUseCase(date).collectLatest {
                    _progressFlow.value = when (it) {
                        is DBResult.Success -> UiState.Success(it.data.asUiModel())
                        is DBResult.Empty -> UiState.Success(ProgressUiModel(0, 0))
                        else -> UiState.Loading
                    }
                }
            }
        }
    }

    private fun setDateFlow(date: LocalDate) {
        viewModelScope.launch {
            _dateFlow.value = date
        }
    }

    private fun setDateItemsFlow(date: LocalDate) {
        viewModelScope.launch {
            _dateItemsFlow.value = listOf(
                UiState.Loading,
                UiState.Success(getDateItemsByDate(date)),
                UiState.Loading
            )
        }
    }

    fun selectDate(dateItem: DateItem) {
        viewModelScope.launch {
            setDateFlow(dateItem.date)
        }
    }

    fun onSelectDate(date: LocalDate) {
        viewModelScope.launch {
            setDateItemsFlow(date)
            setDateFlow(date)
        }
    }

    fun clickDateIcon() {
        viewModelScope.launch {
            _event.emit(HomeEvent.ShowDatePicker(date = _dateFlow.value))
        }
    }

    fun swipeDatePage(position: PageAction) {
        viewModelScope.launch {
            _habitLogsFlow.value = UiState.Loading
            val date = dateFlow.value.plusDays(
                when (position) {
                    PageAction.PREV -> -ONE_WEEK
                    PageAction.NEXT -> ONE_WEEK
                }.toLong()
            ).getStartOfTheWeek()
            delay(DATE_ITEM_DELAY)
            setDateItemsFlow(date)
            setDateFlow(date)
        }
    }

    fun showAddHabit() {
        viewModelScope.launch {
            _event.emit(HomeEvent.ShowAddHabit(habit = HabitUiModel(startDate = dateFlow.value)))
        }
    }

    fun showUpdateHabit(habitWithLog: HabitWithLogUiModel) {
        viewModelScope.launch {
            val id = habitWithLog.habit.id ?: return@launch
            _event.emit(HomeEvent.ShowUpdateHabit(getHabitUseCase(id).asUiModel()))
        }
    }

    fun showDeleteHabit(habitWithLog: HabitWithLogUiModel) {
        viewModelScope.launch {
            _event.emit(HomeEvent.ShowDeleteHabit(habitWithLog.habit))
        }
    }

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

    fun deleteHabitMemo(habitWithLog: HabitWithLogUiModel) {
        viewModelScope.launch {
            deleteHabitMemoUseCase(habitWithLog.habitLog.asDomain())
        }
    }

    fun showMemoEdit(habitWithLog: HabitWithLogUiModel) {
        viewModelScope.launch {
            _event.emit(HomeEvent.ShowMemoEdit(habitWithLog))
        }
    }
}
