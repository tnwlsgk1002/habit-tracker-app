package com.bibbidi.habittracker.domain.repository

import com.bibbidi.habittracker.data.model.DBResult
import com.bibbidi.habittracker.domain.model.HabitLog
import com.bibbidi.habittracker.domain.model.HabitMemo
import kotlinx.coroutines.flow.Flow

interface HabitMemoRepository {

    suspend fun getHabitMemos(id: Long?, reverse: Boolean): Flow<DBResult<List<HabitMemo>>>

    suspend fun saveHabitMemo(habitMemo: HabitMemo, memo: String?)

    suspend fun saveHabitMemo(habitLog: HabitLog, memo: String?)

    suspend fun deleteHabitMemo(logId: Long?)
}
