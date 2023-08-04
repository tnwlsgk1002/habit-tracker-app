package com.bibbidi.habittracker.domain.repository

import com.bibbidi.habittracker.data.model.DBResult
import com.bibbidi.habittracker.domain.model.HabitWithLog
import com.bibbidi.habittracker.domain.model.HabitWithLogs
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDate

interface HabitLogRepository {

    suspend fun insertHabitLog(habitLog: HabitWithLog)

    suspend fun getHabitWithLogsByDate(date: LocalDate): Flow<DBResult<List<HabitWithLog>>>

    suspend fun getHabitWithLogs(id: Long?): Flow<DBResult<HabitWithLogs>>
}
