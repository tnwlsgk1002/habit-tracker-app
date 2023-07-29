package com.bibbidi.habittracker.data.source

import android.util.LongSparseArray
import com.bibbidi.habittracker.data.mapper.asData
import com.bibbidi.habittracker.data.mapper.asDomain
import com.bibbidi.habittracker.data.model.DBResult
import com.bibbidi.habittracker.data.model.entity.HabitLogEntity
import com.bibbidi.habittracker.data.model.entity.HabitWithLogEntity
import com.bibbidi.habittracker.data.model.habit.DailyHabitLogs.Companion.createDailyHabitLogs
import com.bibbidi.habittracker.data.model.habit.Habit
import com.bibbidi.habittracker.data.model.habit.HabitWithLog
import com.bibbidi.habittracker.data.model.habit.HabitWithLogs
import com.bibbidi.habittracker.data.source.database.HabitsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import org.threeten.bp.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultHabitsRepository @Inject constructor(
    private val alarmHelper: AlarmHelper,
    private val dao: HabitsDao
) : HabitsRepository {

    override suspend fun deleteAll() {
        dao.getHabits().forEach {
            alarmHelper.cancelAlarm(it.asDomain())
        }
        dao.deleteAll()
    }

    override suspend fun insertHabit(habit: Habit) {
        val addedHabit = getHabitById(dao.insertHabit(habit.asData()))
        alarmHelper.registerAlarm(addedHabit)
    }

    override suspend fun deleteHabitById(id: Long?) {
        val deletedHabit = getHabitById(id)
        dao.deleteHabitById(id)
        alarmHelper.cancelAlarm(deletedHabit)
    }

    override suspend fun updateHabit(habit: Habit) {
        dao.updateHabit(habit.asData())
        alarmHelper.updateAlarm(habit)
    }

    override suspend fun insertHabitLog(habitLog: HabitWithLog) {
        habitLog.asData().habitLog?.let { dao.insertHabitLog(it) }
    }

    override suspend fun getDailyHabitLogsByDate(date: LocalDate) = flow {
        emit(DBResult.Loading)
        combine(dao.getHabitsByDate(date), dao.getHabitLogsByDate(date)) { habits, logs ->
            val sparseArray = LongSparseArray<HabitLogEntity>().apply {
                logs.forEach { log ->
                    log.habitId?.let { id -> put(id, log) }
                }
            }
            habits.filter { it.repeatDayOfTheWeeks.contains(date.dayOfWeek) }.mapNotNull { habit ->
                habit.id?.let {
                    HabitWithLogEntity(
                        habit,
                        sparseArray[habit.id] ?: HabitLogEntity(habitId = habit.id, date = date)
                    ).asDomain()
                }
            }
        }.map {
            it.sortedWith(compareBy({ it.habitLog.isCompleted }, { it.habit.id }))
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

    override suspend fun getHabitById(id: Long?): Habit {
        return dao.getHabitById(id).asDomain()
    }

    override suspend fun getHabitAlarms(): DBResult<List<Habit>> {
        return DBResult.Success(
            dao.getHabits().map {
                it.asDomain()
            }
        )
    }

    override suspend fun getHabitWithLogs(id: Long?) = flow {
        emit(DBResult.Loading)
        dao.getHabitWithLogs(id).collect() {
            emit(DBResult.Success(it.asDomain()))
        }
    }.catch {
        emit(DBResult.Error(it))
    }

    override suspend fun getHabitResult(
        id: Long?,
        date: LocalDate
    ): Flow<DBResult<HabitWithLogs.HabitResult>> = flow {
        emit(DBResult.Loading)
        dao.getHabitWithLogs(id).collect() {
            emit(DBResult.Success(it.asDomain().getResult(date)))
        }
    }.catch {
        emit(DBResult.Error(it))
    }
}
