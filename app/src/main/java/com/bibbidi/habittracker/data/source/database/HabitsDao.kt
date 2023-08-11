package com.bibbidi.habittracker.data.source.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.bibbidi.habittracker.data.model.habit.dto.HabitMemoDTO
import com.bibbidi.habittracker.data.model.habit.dto.HabitWithLogsDTO
import com.bibbidi.habittracker.data.model.habit.entity.ColorEntity
import com.bibbidi.habittracker.data.model.habit.entity.HabitEntity
import com.bibbidi.habittracker.data.model.habit.entity.HabitLogEntity
import com.bibbidi.habittracker.domain.model.TimeFilter
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDate

@Dao
interface HabitsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: HabitEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabitLog(habitLog: HabitLogEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertColors(colors: List<ColorEntity>)

    @Update
    suspend fun updateHabit(habit: HabitEntity)

    @Query("SELECT * FROM habits")
    suspend fun getHabits(): List<HabitEntity>

    @Transaction
    @Query("SELECT * FROM habits WHERE habit_id = :id")
    suspend fun getHabitById(id: Long?): HabitEntity

    fun getHabitsByDate(date: LocalDate): Flow<List<HabitEntity>> {
        return getHabitsByDate(date, "%${date.dayOfWeek.name}%")
    }

    fun getHabitsByDateAndTimeFilter(date: LocalDate, filter: TimeFilter): Flow<List<HabitEntity>> {
        return getHabitsByDateAndTimeFilter(date, "%${date.dayOfWeek.name}%", "%${filter.name}%")
    }

    @Transaction
    @Query(
        "SELECT * FROM habits " +
            "WHERE habits.start_date <= :date " +
            "AND repeatDayOfTheWeeks LIKE :dayOfWeek " +
            "ORDER BY habits.habit_id DESC"
    )
    fun getHabitsByDate(date: LocalDate, dayOfWeek: String): Flow<List<HabitEntity>>

    @Transaction
    @Query(
        "SELECT * FROM habits " +
            "WHERE habits.start_date <= :date " +
            "AND repeatDayOfTheWeeks LIKE :dayOfWeek " +
            "AND timeFilters LIKE :timeFilter " +
            "ORDER BY habits.habit_id DESC"
    )
    fun getHabitsByDateAndTimeFilter(
        date: LocalDate,
        dayOfWeek: String,
        timeFilter: String
    ): Flow<List<HabitEntity>>

    @Transaction
    @Query(
        "SELECT * FROM habit_logs " +
            "WHERE habit_logs.date = :date "
    )
    fun getHabitLogsByDate(date: LocalDate): Flow<List<HabitLogEntity>>

    @Transaction
    @Query(
        "SELECT * FROM habits " +
            "LEFT JOIN habit_logs ON habits.habit_id = habit_logs.fk_habit_id " +
            "WHERE habits.habit_id = :id " +
            "ORDER BY habit_logs.date ASC"
    )
    fun getHabitWithLogs(id: Long?): Flow<HabitWithLogsDTO>

    @Query(
        "SELECT habit_log_id AS logId, fk_habit_id AS habitId, date AS date, memo AS memo FROM habit_logs " +
            "WHERE fk_habit_id = :id " +
            "AND memo IS NOT NULL " +
            "ORDER BY CASE WHEN :reverse = 1 THEN date END ASC, " +
            "CASE WHEN :reverse = 0 THEN date END DESC"
    )
    fun getHabitMemosById(id: Long?, reverse: Boolean): Flow<List<HabitMemoDTO>>

    @Query(
        "SELECT * FROM habit_logs " +
            "WHERE habit_logs.habit_log_id = :logId "
    )
    suspend fun getHabitLogByLogId(logId: Long?): HabitLogEntity?

    @Query("SELECT * FROM habit_colors ORDER BY color_id ASC")
    fun getAllColors(): Flow<List<ColorEntity>>

    @Query("DELETE FROM habits WHERE habit_id = :id")
    suspend fun deleteHabitById(id: Long?)

    @Query("DELETE FROM habits")
    suspend fun deleteAll()

    @Query("UPDATE habit_logs SET memo = :memo WHERE habit_log_id = :logId")
    suspend fun updateHabitMemo(logId: Long?, memo: String?)
}
