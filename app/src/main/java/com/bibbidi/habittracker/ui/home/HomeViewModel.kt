package com.bibbidi.habittracker.ui.home

import androidx.lifecycle.ViewModel
import com.bibbidi.habittracker.domain.HabitsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val habitsRepository: HabitsRepository
) : ViewModel()
