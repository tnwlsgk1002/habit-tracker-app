package com.bibbidi.habittracker.domain.repository

import com.bibbidi.habittracker.domain.model.Habit

interface HabitRepository {

    suspend fun insertHabit(habit: Habit): Habit

    suspend fun deleteHabitById(id: Long?)

    suspend fun updateHabit(habit: Habit)

    suspend fun getHabitById(id: Long?): Habit

    suspend fun getHabits(): List<Habit>
}
