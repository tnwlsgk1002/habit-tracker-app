package com.bibbidi.habittracker.data.source

import com.bibbidi.habittracker.data.model.DBResult
import com.bibbidi.habittracker.data.model.habit.DailyHabitLogs
import com.bibbidi.habittracker.data.model.habit.Habit
import com.bibbidi.habittracker.data.model.habit.HabitLog
import com.bibbidi.habittracker.data.model.habit.HabitMemo
import com.bibbidi.habittracker.data.model.habit.HabitResult
import com.bibbidi.habittracker.data.model.habit.HabitWithLog
import com.bibbidi.habittracker.data.model.habit.HabitWithLogs
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDate

interface HabitsRepository {

    suspend fun insertHabit(habit: Habit)

    suspend fun deleteHabitById(id: Long?)

    suspend fun updateHabit(habit: Habit)

    suspend fun getHabitById(id: Long?): Habit

    suspend fun getDailyHabitLogsByDate(date: LocalDate): Flow<DBResult<DailyHabitLogs>>

    suspend fun insertHabitLog(habitLog: HabitWithLog)

    suspend fun deleteAll()

    suspend fun getHabitAlarms(): DBResult<List<Habit>>

    suspend fun getHabitWithLogs(id: Long?): Flow<DBResult<HabitWithLogs>>

    suspend fun getHabitMemos(id: Long?, reverse: Boolean): Flow<DBResult<List<HabitMemo>>>

    suspend fun saveHabitMemo(habitMemo: HabitMemo, memo: String?)

    suspend fun saveHabitMemo(habitLog: HabitLog, memo: String?)

    suspend fun deleteHabitMemo(logId: Long?)

    suspend fun getHabitResult(
        id: Long?,
        date: LocalDate
    ): Flow<DBResult<HabitResult>>
}
