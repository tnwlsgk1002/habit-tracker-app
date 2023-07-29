package com.bibbidi.habittracker.data.source.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.bibbidi.habittracker.data.model.entity.HabitEntity
import com.bibbidi.habittracker.data.model.entity.HabitLogEntity
import com.bibbidi.habittracker.data.model.entity.HabitWithLogsEntity
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDate

@Dao
interface HabitsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: HabitEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabitLog(habitLog: HabitLogEntity): Long

    @Update
    suspend fun updateHabit(habit: HabitEntity)

    @Query("SELECT * FROM habits")
    suspend fun getHabits(): List<HabitEntity>

    @Transaction
    @Query("SELECT * FROM habits WHERE habit_id = :id")
    suspend fun getHabitById(id: Long?): HabitEntity

    @Transaction
    @Query(
        "SELECT * FROM habits " +
            "WHERE habits.start_date <= :date " +
            "ORDER BY habits.habit_id DESC"
    )
    fun getHabitsByDate(date: LocalDate): Flow<List<HabitEntity>>

    @Transaction
    @Query(
        "SELECT * FROM habit_logs " +
            "WHERE habit_logs.date = :date "
    )
    fun getHabitLogsByDate(date: LocalDate): Flow<List<HabitLogEntity>>

    @Transaction
    @Query(
        "SELECT * FROM habits " +
            "INNER JOIN habit_logs ON habits.habit_id = habit_logs.fk_habit_id " +
            "WHERE habits.habit_id = :id " +
            "ORDER BY habit_logs.date ASC"
    )
    fun getHabitWithLogs(id: Long?): Flow<HabitWithLogsEntity>

    @Query("DELETE FROM habits WHERE habit_id = :id")
    suspend fun deleteHabitById(id: Long?)

    @Query("DELETE FROM habits")
    suspend fun deleteAll()
}
