package com.bibbidi.habittracker.data.source

import javax.inject.Inject

class DefaultHabitsRepository @Inject constructor(
    private val habitsLocalDataSource: HabitsDataSource
) : HabitsRepository
