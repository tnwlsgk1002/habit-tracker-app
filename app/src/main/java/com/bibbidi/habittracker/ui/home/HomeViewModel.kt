package com.bibbidi.habittracker.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bibbidi.habittracker.domain.HabitsRepository
import com.bibbidi.habittracker.ui.common.MutableEventFlow
import com.bibbidi.habittracker.ui.mapper.habitinfo.asDomain
import com.bibbidi.habittracker.ui.model.habit.habitinfo.HabitInfoUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val habitsRepository: HabitsRepository
) : ViewModel() {

    val messageEvent: MutableEventFlow<HomeMessageEvent> = MutableEventFlow()

    fun setHabit(habitInfo: HabitInfoUiModel) {
        viewModelScope.launch {
            habitsRepository.insertHabit(habitInfo.asDomain())
            messageEvent.emit(HomeMessageEvent.SuccessAddHabit)
        }
    }
}
