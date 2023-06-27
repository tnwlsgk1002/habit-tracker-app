package com.bibbidi.habittracker.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibbidi.habittracker.domain.HabitsRepository
import com.bibbidi.habittracker.domain.model.DBResult
import com.bibbidi.habittracker.domain.model.log.HabitLog
import com.bibbidi.habittracker.ui.common.Constants.ONE_WEEK
import com.bibbidi.habittracker.ui.common.Constants.ROW_CALENDAR_SIZE
import com.bibbidi.habittracker.ui.common.EventFlow
import com.bibbidi.habittracker.ui.common.MutableEventFlow
import com.bibbidi.habittracker.ui.common.UiState
import com.bibbidi.habittracker.ui.common.asEventFlow
import com.bibbidi.habittracker.ui.mapper.habitinfo.asDomain
import com.bibbidi.habittracker.ui.mapper.habitinfo.asUiModel
import com.bibbidi.habittracker.ui.mapper.habitlog.asDomain
import com.bibbidi.habittracker.ui.mapper.habitlog.asUiModel
import com.bibbidi.habittracker.ui.model.date.DateItem
import com.bibbidi.habittracker.ui.model.date.getDateItemsByDate
import com.bibbidi.habittracker.ui.model.habit.habitinfo.HabitInfoUiModel
import com.bibbidi.habittracker.ui.model.habit.log.CheckHabitLogUiModel
import com.bibbidi.habittracker.ui.model.habit.log.HabitLogUiModel
import com.bibbidi.habittracker.ui.model.habit.log.TrackHabitLogUiModel
import com.bibbidi.habittracker.utils.getStartOfTheWeek
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
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

    private val _dateItemsFlow: MutableStateFlow<List<UiState<Array<DateItem>>>> = MutableStateFlow(
        List(ROW_CALENDAR_SIZE) { UiState.Loading }
    )
    val dateItemsFlow: StateFlow<List<UiState<Array<DateItem>>>> = _dateItemsFlow.asStateFlow()

    private val habits = MutableStateFlow<DBResult<List<HabitLog>>>(DBResult.Loading)

//    private val _habitsStateFlow: MutableStateFlow<UiState<List<HabitLogUiModel>>> =
//        MutableStateFlow(UiState.Loading)
//
//    val habitsStateFlow: StateFlow<UiState<List<HabitLogUiModel>>> = _habitsStateFlow.asStateFlow()

    val habitsStateFlow = combine(
        dateFlow,
        habits
    ) { _, habits ->
        when (habits) {
            is DBResult.Success -> {
                UiState.Success(habits.data.map { it.asUiModel() })
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
            dateFlow.flatMapLatest { date ->
                habitsRepository.getHabitAndHabitLogsByDate(date)
            }.collectLatest { result ->
                habits.value = result
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

    fun pickDate(date: LocalDate) {
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

    fun clickDateItem(dateItem: DateItem) {
        viewModelScope.launch {
            setDate(dateItem.date)
        }
    }

    fun swipeDatePages(position: PageAction) {
        viewModelScope.launch {
            val date = when (position) {
                PageAction.PREV -> dateFlow.value.plusDays(-ONE_WEEK.toLong())
                PageAction.NEXT -> dateFlow.value.plusDays(ONE_WEEK.toLong())
            }.getStartOfTheWeek()
            delay(DATE_ITEM_DELAY)
            setDateItems(date)
            setDate(date)
        }
    }

    fun clickAddHabit() {
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

    fun onUpdateHabitClicked(habitLog: HabitLogUiModel) {
        viewModelScope.launch {
            val id = habitLog.habitId ?: return@launch
            val habit = habitsRepository.getHabitById(id)
            _event.emit(HomeEvent.AttemptUpdateHabit(habit.asUiModel()))
        }
    }

    fun onDeleteHabitClicked(habitLog: HabitLogUiModel) {
        viewModelScope.launch {
            _event.emit(HomeEvent.AttemptDeleteHabit(habitLog))
        }
    }

    fun deleteHabit(habitLog: HabitLogUiModel) {
        viewModelScope.launch {
            habitLog.habitId?.let { habitsRepository.deleteHabitById(it) }
        }
    }

    fun updateHabit(habitInfo: HabitInfoUiModel) {
        viewModelScope.launch {
            habitsRepository.updateHabit(habitInfo.asDomain())
        }
    }

    fun updateCheckHabitLog(log: CheckHabitLogUiModel, isChecked: Boolean) {
        viewModelScope.launch {
            habitsRepository.updateHabitLog(log.copy(isChecked = isChecked).asDomain())
        }
    }

    fun showInputTrackHabitValue(log: TrackHabitLogUiModel) {
        viewModelScope.launch {
            _event.emit(HomeEvent.ShowTrackValueDialog(log))
        }
    }

    fun updateTrackHabitLog(log: TrackHabitLogUiModel, value: Long?) {
        viewModelScope.launch {
            habitsRepository.updateHabitLog(log.copy(value = value).asDomain())
        }
    }
}
