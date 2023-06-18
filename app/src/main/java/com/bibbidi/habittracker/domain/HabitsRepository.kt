package com.bibbidi.habittracker.domain

import com.bibbidi.habittracker.domain.model.DBResult
import com.bibbidi.habittracker.domain.model.habitinfo.HabitInfo
import com.bibbidi.habittracker.domain.model.log.HabitLog
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDate

interface HabitsRepository {

    suspend fun insertHabit(habitInfo: HabitInfo)

    suspend fun deleteHabitById(id: Long)

    suspend fun updateHabit(habit: HabitInfo)

    suspend fun getHabitById(id: Long): HabitInfo

    suspend fun getHabitAndHabitLogsByDate(date: LocalDate): Flow<DBResult<List<HabitLog>>>

    suspend fun updateHabitLog(habitLog: HabitLog)

    suspend fun deleteAll()
}
