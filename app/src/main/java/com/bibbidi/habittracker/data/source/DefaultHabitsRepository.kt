package com.bibbidi.habittracker.data.source

import com.bibbidi.habittracker.data.mapper.asData
import com.bibbidi.habittracker.data.mapper.check.asData
import com.bibbidi.habittracker.data.mapper.time.asData
import com.bibbidi.habittracker.data.mapper.track.asData
import com.bibbidi.habittracker.data.model.entity.check.CheckHabitEntity
import com.bibbidi.habittracker.data.model.entity.time.TimeHabitEntity
import com.bibbidi.habittracker.data.model.entity.track.TrackHabitEntity
import com.bibbidi.habittracker.data.source.database.HabitsDao
import com.bibbidi.habittracker.domain.HabitsRepository
import com.bibbidi.habittracker.domain.model.DBResult
import com.bibbidi.habittracker.domain.model.habitinfo.CheckHabitInfo
import com.bibbidi.habittracker.domain.model.habitinfo.HabitInfo
import com.bibbidi.habittracker.domain.model.habitinfo.TimeHabitInfo
import com.bibbidi.habittracker.domain.model.habitinfo.TrackHabitInfo
import com.bibbidi.habittracker.domain.model.log.CheckHabitLog
import com.bibbidi.habittracker.domain.model.log.HabitLog
import com.bibbidi.habittracker.domain.model.log.TimeHabitLog
import com.bibbidi.habittracker.domain.model.log.TrackHabitLog
import kotlinx.coroutines.flow.flow
import org.threeten.bp.LocalDate
import javax.inject.Inject

class DefaultHabitsRepository @Inject constructor(
    private val dao: HabitsDao
) : HabitsRepository {

    override suspend fun deleteAll() {
        dao.deleteAll()
    }

    override suspend fun insertHabit(habitInfo: HabitInfo) {
        when (habitInfo) {
            is CheckHabitInfo -> dao.insertHabitAndCheckHabit(habitInfo.asData())
            is TimeHabitInfo -> dao.insertHabitAndTimeHabit(habitInfo.asData())
            is TrackHabitInfo -> dao.insertHabitAndTrackHabit(habitInfo.asData())
        }
    }

    override suspend fun deleteHabitById(id: Long) {
        dao.deleteHabitById(id)
    }

    override suspend fun updateHabit(habit: HabitInfo) {
        dao.updateHabits(habit.asData())
    }

    override suspend fun getHabitAndHabitLogsByDate(date: LocalDate) = flow {
        emit(DBResult.Loading)
        dao.getHabitAndChildren(date).collect { habitAndChildren ->
            habitAndChildren.filter { it.habit.repeatDayOfTheWeeks.contains(date.dayOfWeek) }
                .runCatching {
                    map {
                        when (
                            val type = listOfNotNull(
                                it.timeHabit,
                                it.trackHabit,
                                it.checkHabit
                            ).firstOrNull()
                        ) {
                            is CheckHabitEntity -> {
                                val log = dao.getCheckLogByCheckHabitIdInDateTransaction(
                                    type.checkHabitId,
                                    date
                                )
                                CheckHabitLog.createHabitLog(it.habit, type, log)
                            }
                            is TimeHabitEntity -> {
                                val log = dao.getTimeLogByTimeHabitIdInDateTransaction(
                                    type.timeHabitId,
                                    date
                                )
                                TimeHabitLog.createHabitLog(it.habit, type, log)
                            }
                            is TrackHabitEntity -> {
                                val log = dao.getTrackLogByTrackHabitIdInDateTransaction(
                                    type.trackHabitId,
                                    date
                                )
                                TrackHabitLog.createHabitLog(it.habit, type, log)
                            }
                            else -> throw IllegalStateException()
                        }
                    }
                }.onSuccess {
                    if (it.isEmpty()) {
                        emit(DBResult.Empty)
                    } else {
                        emit(DBResult.Success(it))
                    }
                }.onFailure {
                    emit(DBResult.Error(it))
                }
        }
    }

    override suspend fun updateHabitLog(habitLog: HabitLog) {
        when (habitLog) {
            is CheckHabitLog -> dao.updateCheckHabitLog(habitLog.asData())
            is TimeHabitLog -> dao.updateTimeHabitLog(habitLog.asData())
            is TrackHabitLog -> dao.updateTrackHabitLog(habitLog.asData())
        }
    }
}
