package com.bibbidi.habittracker.data.source

import android.util.LongSparseArray
import com.bibbidi.habittracker.data.mapper.asData
import com.bibbidi.habittracker.data.mapper.asDomain
import com.bibbidi.habittracker.data.model.DBResult
import com.bibbidi.habittracker.data.model.HabitWithHabitLog
import com.bibbidi.habittracker.data.model.entity.HabitLogEntity
import com.bibbidi.habittracker.data.model.habit.DailyHabitLogs.Companion.createDailyHabitLogs
import com.bibbidi.habittracker.data.model.habit.Habit
import com.bibbidi.habittracker.data.model.habit.HabitLog
import com.bibbidi.habittracker.data.source.database.HabitsDao
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.threeten.bp.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultHabitsRepository @Inject constructor(
    private val dao: HabitsDao
) : HabitsRepository {

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    override suspend fun insertHabit(habit: Habit): Habit {
        return getHabitById(dao.insertHabit(habit.asData()))
    }

    override suspend fun deleteHabitById(id: Long): Habit {
        return getHabitById(id).also { dao.deleteHabitById(id) }
    }

    override suspend fun insertHabitLog(habitLog: HabitLog) {
        habitLog.asData().habitLog?.let { dao.insertHabitLog(it) }
    }

    override suspend fun getHabitWithHabitLogsByDate(date: LocalDate) = flow {
        emit(DBResult.Loading)
        combine(dao.getHabitsByDate(date), dao.getHabitLogsByDate(date)) { habits, logs ->
            val sparseArray = LongSparseArray<HabitLogEntity>().apply {
                logs.forEach { log ->
                    log.habitId?.let { id -> put(id, log) }
                }
            }
            habits.filter { it.repeatDayOfTheWeeks.contains(date.dayOfWeek) }.mapNotNull { habit ->
                habit.id?.let {
                    HabitWithHabitLog(
                        habit,
                        sparseArray[habit.id] ?: HabitLogEntity(habitId = habit.id, date = date)
                    ).asDomain()
                }
            }
        }.map {
            it.sortedWith(compareBy({ log -> log.isCompleted }, { log -> log.habitInfo.id }))
        }.collect {
            if (it.isEmpty()) {
                emit(DBResult.Empty)
            } else {
                emit(DBResult.Success(createDailyHabitLogs(it)))
            }
        }
    }.catch {
        emit(DBResult.Error(it))
    }

    override suspend fun getHabitById(id: Long): Habit {
        return dao.getHabitById(id).asDomain()
    }

    override suspend fun updateHabit(habit: Habit): Habit {
        dao.updateHabit(habit.asData())
        return dao.getHabitById(habit.id).asDomain()
    }

    override suspend fun getHabitAlarms(): DBResult<List<Habit>> {
        return DBResult.Success(
            dao.getHabits().map {
                it.asDomain()
            }
        )
    }
}
