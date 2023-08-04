package com.bibbidi.habittracker.data.source

import android.util.LongSparseArray
import com.bibbidi.habittracker.data.mapper.asData
import com.bibbidi.habittracker.data.mapper.asDomain
import com.bibbidi.habittracker.data.model.DBResult
import com.bibbidi.habittracker.data.model.habit.dto.HabitWithLogDTO
import com.bibbidi.habittracker.data.model.habit.entity.HabitLogEntity
import com.bibbidi.habittracker.data.source.database.HabitsDao
import com.bibbidi.habittracker.domain.model.HabitWithLog
import com.bibbidi.habittracker.domain.repository.HabitLogRepository
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

    override suspend fun getHabitWithLogsByDate(date: LocalDate) = flow {
        emit(DBResult.Loading)
        combine(dao.getHabitsByDate(date), dao.getHabitLogsByDate(date)) { habits, logs ->
            val sparseArray = LongSparseArray<HabitLogEntity>().apply {
                logs.forEach { log ->
                    log.habitId?.let { id -> put(id, log) }
                }
            }
            habits.filter { it.repeatDayOfTheWeeks.contains(date.dayOfWeek) }.mapNotNull { habit ->
                habit.id?.let {
                    HabitWithLogDTO(
                        habit,
                        sparseArray[habit.id] ?: HabitLogEntity(habitId = habit.id, date = date)
                    ).asDomain()
                }
            }
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
