package com.bibbidi.habittracker.data.source.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.bibbidi.habittracker.data.model.entity.HabitEntity
import com.bibbidi.habittracker.data.model.entity.check.CheckHabitEntity
import com.bibbidi.habittracker.data.model.entity.check.CheckHabitLogEntity
import com.bibbidi.habittracker.data.model.entity.time.TimeHabitEntity
import com.bibbidi.habittracker.data.model.entity.time.TimeHabitLogEntity
import com.bibbidi.habittracker.data.model.entity.track.TrackHabitEntity
import com.bibbidi.habittracker.data.model.entity.track.TrackHabitLogEntity
import com.bibbidi.habittracker.data.model.joined.HabitAndChildren
import com.bibbidi.habittracker.data.model.joined.check.HabitAndCheckHabitEntity
import com.bibbidi.habittracker.data.model.joined.time.HabitAndTimeHabitEntity
import com.bibbidi.habittracker.data.model.joined.track.HabitAndTrackHabitEntity
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDate

@Dao
interface HabitsDao {

    @Insert
    suspend fun insertHabit(habit: HabitEntity): Long

    @Insert
    suspend fun insertCheckHabit(checkHabit: CheckHabitEntity): Long

    @Insert
    suspend fun insertTimeHabit(timeHabitEntity: TimeHabitEntity)

    @Insert
    suspend fun insertTrackHabit(trackHabitEntity: TrackHabitEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCheckHabitLog(log: CheckHabitLogEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimeHabitLog(log: TimeHabitLogEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackHabitLog(log: TrackHabitLogEntity)

    @Transaction
    suspend fun insertHabitAndCheckHabit(habitAndCheckHabitEntity: HabitAndCheckHabitEntity) {
        val habitId = insertHabit(habitAndCheckHabitEntity.habit)
        insertCheckHabit(habitAndCheckHabitEntity.checkHabit.copy(habitId = habitId))
    }

    @Transaction
    suspend fun insertHabitAndTimeHabit(habitAndTimeHabitEntity: HabitAndTimeHabitEntity) {
        val habitId = insertHabit(habitAndTimeHabitEntity.habit)
        insertTimeHabit(habitAndTimeHabitEntity.timeHabit.copy(habitId = habitId))
    }

    @Transaction
    suspend fun insertHabitAndTrackHabit(habitAndTrackHabitEntity: HabitAndTrackHabitEntity) {
        val habitId = insertHabit(habitAndTrackHabitEntity.habit)
        insertTrackHabit(habitAndTrackHabitEntity.trackHabit.copy(habitId = habitId))
    }

    @Update
    suspend fun updateHabits(vararg habits: HabitEntity)

    @Update
    suspend fun updateCheckHabitLog(habitLog: CheckHabitLogEntity)

    @Update
    suspend fun updateTimeHabitLog(habitLog: TimeHabitLogEntity)

    @Update
    suspend fun updateTrackHabitLog(habitLog: TrackHabitLogEntity)

    @Transaction
    @Query("SELECT * FROM habits WHERE start_date <= :date")
    fun getHabitAndChildren(date: LocalDate): Flow<List<HabitAndChildren>>

    @Query("SELECT * FROM check_habit_logs WHERE date = :date AND check_habit_parent_id = :id")
    suspend fun getCheckLogByCheckHabitIdInDate(id: Long?, date: LocalDate): CheckHabitLogEntity?

    @Transaction
    suspend fun getCheckLogByCheckHabitIdInDateTransaction(
        id: Long?,
        date: LocalDate
    ): CheckHabitLogEntity {
        return getCheckLogByCheckHabitIdInDate(id, date)
            ?: CheckHabitLogEntity(
                checkHabitId = id,
                date = date
            ).also { insertCheckHabitLog(it) }
    }

    @Query("SELECT * FROM time_habit_logs WHERE date = :date AND time_habit_parent_id = :id")
    suspend fun getTimeLogByTimeHabitIdInDate(id: Long?, date: LocalDate): TimeHabitLogEntity?

    @Transaction
    suspend fun getTimeLogByTimeHabitIdInDateTransaction(id: Long?, date: LocalDate): TimeHabitLogEntity {
        return getTimeLogByTimeHabitIdInDate(id, date)
            ?: TimeHabitLogEntity(
                timeHabitId = id,
                date = date
            ).also { insertTimeHabitLog(it) }
    }

    @Query("SELECT * FROM track_habit_logs WHERE date = :date AND track_habit_parent_id = :id")
    suspend fun getTrackLogByTrackHabitIdInDate(id: Long?, date: LocalDate): TrackHabitLogEntity?

    @Transaction
    suspend fun getTrackLogByTrackHabitIdInDateTransaction(id: Long?, date: LocalDate): TrackHabitLogEntity {
        return getTrackLogByTrackHabitIdInDate(id, date)
            ?: TrackHabitLogEntity(
                trackHabitId = id,
                date = date
            ).also { insertTrackHabitLog(it) }
    }

    @Query("DELETE FROM habits WHERE habit_id = :id")
    suspend fun deleteHabitById(id: Long)

    @Query("DELETE FROM habits")
    suspend fun deleteAll()
}
