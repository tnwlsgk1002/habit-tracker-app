package com.bibbidi.habittracker.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibbidi.habittracker.data.model.DBResult
import com.bibbidi.habittracker.data.model.habit.DailyHabitLogs
import com.bibbidi.habittracker.data.source.HabitsRepository
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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val habitsRepository: HabitsRepository
) : ViewModel() {

    companion object {
        const val DATE_ITEM_DELAY = 500L
    }

    private val _dateFlow: MutableStateFlow<LocalDate> = MutableStateFlow(LocalDate.now())
    val dateFlow: StateFlow<LocalDate> = _dateFlow.asStateFlow()

    private val _dateItemsFlow: MutableStateFlow<List<UiState<Array<DateItem>>>> =
        MutableStateFlow(List(ROW_CALENDAR_SIZE) { UiState.Loading })

    val dateItemsFlow: StateFlow<List<UiState<Array<DateItem>>>> = _dateItemsFlow.asStateFlow()

    private val habits = MutableStateFlow<DBResult<DailyHabitLogs>>(DBResult.Loading)

    val progressFlow = MutableStateFlow(ProgressUiModel())

    val habitsStateFlow = combine(
        dateFlow,
        habits
    ) { _, habits ->
        when (habits) {
            is DBResult.Success -> {
                UiState.Success(habits.data.logs.map { it.asUiModel() })
            }
            is DBResult.Loading -> UiState.Loading
            is DBResult.Empty -> UiState.Empty
            else -> UiState.Empty
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, UiState.Loading)

    private val _event: MutableEventFlow<HomeEvent> = MutableEventFlow()
    val event: EventFlow<HomeEvent> = _event.asEventFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            setDateItems(dateFlow.value)
            dateFlow.collectLatest { date ->
                habitsRepository.getDailyHabitLogsByDate(date).collectLatest {
                    habits.value = it
                    progressFlow.value = if (it is DBResult.Success) {
                        ProgressUiModel(it.data.finishCount, it.data.total)
                    } else {
                        ProgressUiModel(0, 0)
                    }
                }
            }
        }
    }

    private fun setDate(date: LocalDate) {
        viewModelScope.launch {
            _dateFlow.value = date
        }
    }

    private fun setDateItems(date: LocalDate) {
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
            setDate(dateItem.date)
        }
    }

    fun onSelectDate(date: LocalDate) {
        viewModelScope.launch {
            setDateItems(date)
            setDate(date)
        }
    }

    fun clickDateIcon() {
        viewModelScope.launch {
            _event.emit(HomeEvent.ShowDatePicker(date = _dateFlow.value))
        }
    }

    fun swipeDatePage(position: PageAction) {
        viewModelScope.launch {
            habits.value = DBResult.Loading
            val date = when (position) {
                PageAction.PREV -> dateFlow.value.plusDays(-ONE_WEEK.toLong())
                PageAction.NEXT -> dateFlow.value.plusDays(ONE_WEEK.toLong())
            }.getStartOfTheWeek()
            delay(DATE_ITEM_DELAY)
            setDateItems(date)
            setDate(date)
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
            val habit = habitsRepository.getHabitById(id)
            _event.emit(HomeEvent.ShowUpdateHabit(habit.asUiModel()))
        }
    }

    fun showDeleteHabit(habitWithLog: HabitWithLogUiModel) {
        viewModelScope.launch {
            _event.emit(HomeEvent.ShowDeleteHabit(habitWithLog.habit))
        }
    }

    fun updateHabitLog(log: HabitWithLogUiModel, isChecked: Boolean) {
        viewModelScope.launch {
            habitsRepository.insertHabitLog(
                log.copy(habitLog = log.habitLog.copy(isCompleted = isChecked)).asDomain()
            )
        }
    }

    fun saveHabitMemo(habitWithLog: HabitWithLogUiModel, memo: String?) {
        viewModelScope.launch {
            habitsRepository.saveHabitMemo(habitWithLog.habitLog.asDomain(), memo)
        }
    }

    fun showMemoEdit(habitWithLog: HabitWithLogUiModel) {
        viewModelScope.launch {
            _event.emit(HomeEvent.ShowMemoEdit(habitWithLog))
        }
    }
}
