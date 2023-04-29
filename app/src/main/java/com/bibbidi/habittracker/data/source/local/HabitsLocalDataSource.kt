package com.bibbidi.habittracker.data.source.local

import com.bibbidi.habittracker.data.source.HabitsDataSource
import javax.inject.Inject

class HabitsLocalDataSource @Inject constructor(
    private val habitsDao: HabitsDao
) : HabitsDataSource
