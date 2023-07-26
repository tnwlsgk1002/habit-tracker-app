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
import com.bibbidi.habittracker.ui.model.habit.HabitLogUiModel
import com.bibbidi.habittracker.ui.model.habit.HabitUiModel
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
                habitsRepository.getHabitWithHabitLogsByDate(date).collectLatest {
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

    fun deleteHabit(habitLog: HabitLogUiModel) {
        viewModelScope.launch {
            habitLog.habitInfo.id?.let {
                val habit = habitsRepository.deleteHabitById(it).asUiModel()
                _event.emit(HomeEvent.SuccessDeleteHabit(habit))
            }
        }
    }

    fun updateHabit(habit: HabitUiModel) {
        viewModelScope.launch {
            val habit = habitsRepository.updateHabit(habit.asDomain())
            _event.emit(HomeEvent.SuccessUpdateHabit(habit.asUiModel()))
        }
    }

    fun updateHabitLog(log: HabitLogUiModel, isChecked: Boolean) {
        viewModelScope.launch {
            habitsRepository.insertHabitLog(log.copy(isCompleted = isChecked).asDomain())
        }
    }

    fun onSelectDate(date: LocalDate) {
        viewModelScope.launch {
            setDateItems(date)
            setDate(date)
        }
    }

    fun onSelectDate(dateItem: DateItem) {
        viewModelScope.launch {
            setDate(dateItem.date)
        }
    }

    fun clickDateIcon() {
        viewModelScope.launch {
            _event.emit(HomeEvent.ShowDatePicker(date = _dateFlow.value))
        }
    }

    fun onSwipeDatePage(position: PageAction) {
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

    fun onAddHabit() {
        viewModelScope.launch {
            _event.emit(HomeEvent.AttemptAddHabit(habitInfo = HabitUiModel(startDate = dateFlow.value)))
        }
    }

    fun onSetHabit(habit: HabitUiModel) {
        viewModelScope.launch {
            habitsRepository.insertHabit(habit.asDomain())
            _event.emit(HomeEvent.SuccessAddHabit(habit))
        }
    }

    fun onUpdateHabit(habitLog: HabitLogUiModel) {
        viewModelScope.launch {
            val id = habitLog.habitInfo.id ?: return@launch
            val habit = habitsRepository.getHabitById(id)
            _event.emit(HomeEvent.AttemptUpdateHabit(habit.asUiModel()))
        }
    }

    fun onDeleteHabit(habitLog: HabitLogUiModel) {
        viewModelScope.launch {
            _event.emit(HomeEvent.AttemptDeleteHabit(habitLog))
        }
    }
}
