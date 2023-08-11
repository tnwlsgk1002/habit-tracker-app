package com.bibbidi.habittracker.data.source

import android.util.LongSparseArray
import com.bibbidi.habittracker.data.mapper.asData
import com.bibbidi.habittracker.data.mapper.asDomain
import com.bibbidi.habittracker.data.model.DBResult
import com.bibbidi.habittracker.data.model.habit.dto.HabitWithLogDTO
import com.bibbidi.habittracker.data.model.habit.entity.HabitEntity
import com.bibbidi.habittracker.data.model.habit.entity.HabitLogEntity
import com.bibbidi.habittracker.data.source.database.HabitsDao
import com.bibbidi.habittracker.domain.model.HabitWithLog
import com.bibbidi.habittracker.domain.model.TimeFilter
import com.bibbidi.habittracker.domain.repository.HabitLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.threeten.bp.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HabitLogRepositoryImpl @Inject constructor(
    private val dao: HabitsDao
) : HabitLogRepository {

    override suspend fun insertHabitLog(habitLog: HabitWithLog) {
        habitLog.asData().habitLog?.let { dao.insertHabitLog(it) }
    }

    private suspend fun getHabitsWithLogByDate(
        habitsFlow: Flow<List<HabitEntity>>,
        logsFlow: Flow<List<HabitLogEntity>>,
        date: LocalDate
    ): Flow<DBResult<List<HabitWithLog>>> = flow {
        emit(DBResult.Loading)
        combine(habitsFlow, logsFlow) { habits, logs ->
            val sparseArray = LongSparseArray<HabitLogEntity>().apply {
                logs.forEach { log ->
                    log.habitId?.let { id -> put(id, log) }
                }
            }
            habits.mapNotNull { habit ->
                habit.id?.let {
                    HabitWithLogDTO(
                        habit,
                        sparseArray[habit.id] ?: HabitLogEntity(habitId = habit.id, date = date)
                    ).asDomain()
                }
            }.sortedWith(compareBy({ it.habitLog.isCompleted }, { it.habit.id }))
        }.collect {
            if (it.isEmpty()) {
                emit(DBResult.Empty)
            } else {
                emit(DBResult.Success(it))
            }
        }
    }.catch {
        emit(DBResult.Error(it))
    }

    override suspend fun getAllHabitWithLogByDate(date: LocalDate) =
        getHabitsWithLogByDate(dao.getHabitsByDate(date), dao.getHabitLogsByDate(date), date)

    override suspend fun getFilteredHabitWithLogByDate(date: LocalDate, timeFilter: TimeFilter) =
        getHabitsWithLogByDate(
            dao.getHabitsByDateAndTimeFilter(date, timeFilter),
            dao.getHabitLogsByDate(date),
            date
        )

    override suspend fun getHabitWithLogs(id: Long?) = flow {
        emit(DBResult.Loading)
        dao.getHabitWithLogs(id).map {
            it.copy(habitLogs = it.habitLogs.sortedBy { it.date })
        }.collect() {
            emit(DBResult.Success(it.asDomain()))
        }
    }.catch {
        emit(DBResult.Error(it))
    }
}
