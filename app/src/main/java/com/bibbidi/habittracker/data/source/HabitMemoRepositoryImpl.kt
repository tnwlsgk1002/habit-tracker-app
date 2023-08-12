package com.bibbidi.habittracker.data.source

import com.bibbidi.habittracker.data.mapper.asData
import com.bibbidi.habittracker.data.mapper.asDomain
import com.bibbidi.habittracker.data.model.DBResult
import com.bibbidi.habittracker.data.source.database.HabitsDao
import com.bibbidi.habittracker.domain.model.HabitLog
import com.bibbidi.habittracker.domain.model.HabitMemo
import com.bibbidi.habittracker.domain.repository.HabitMemoRepository
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HabitMemoRepositoryImpl @Inject constructor(
    private val dao: HabitsDao
) : HabitMemoRepository {

    override suspend fun getHabitMemos(
        id: Long?,
        reverse: Boolean
    ) = flow {
        emit(DBResult.Loading)
        dao.getHabitMemosById(id, reverse).collect() {
            if (it.isNotEmpty()) {
                emit(DBResult.Success(it.map { it.asDomain() }))
            } else {
                emit(DBResult.Empty)
            }
        }
    }.catch {
        emit(DBResult.Error(it))
    }

    override suspend fun deleteHabitMemo(logId: Long?) {
        dao.getHabitLogByLogId(logId)?.let {
            dao.insertHabitLog(it.copy(memo = null))
        }
    }

    override suspend fun saveHabitMemo(habitLog: HabitLog, memo: String?) {
        val newMemo = if (memo.isNullOrEmpty()) null else memo
        dao.insertHabitLog(habitLog.copy(memo = newMemo).asData())
    }

    override suspend fun saveHabitMemo(habitMemo: HabitMemo, memo: String?) {
        dao.updateHabitMemo(habitMemo.logId, memo)
    }
}
