package com.bibbidi.habittracker.data.source

import com.bibbidi.habittracker.data.model.DBResult
import com.bibbidi.habittracker.data.model.habit.Habit
import com.bibbidi.habittracker.data.model.habit.HabitLog
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDate

interface HabitsRepository {

    suspend fun insertHabit(habit: Habit): Habit

    suspend fun deleteHabitById(id: Long): Habit

    suspend fun updateHabit(habit: Habit): Habit

    suspend fun getHabitById(id: Long): Habit

    suspend fun getHabitWithHabitLogsByDate(date: LocalDate): Flow<DBResult<List<HabitLog>>>

    suspend fun insertHabitLog(habitLog: HabitLog)

    suspend fun deleteAll()

    suspend fun getHabitAlarms(): DBResult<List<Habit>>
}
