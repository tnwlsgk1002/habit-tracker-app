package com.bibbidi.habittracker.ui.addhabit

import androidx.lifecycle.ViewModel
import com.bibbidi.habittracker.data.source.HabitsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SetHabitViewModel @Inject constructor(
    private val habitsRepository: HabitsRepository
) : ViewModel()
