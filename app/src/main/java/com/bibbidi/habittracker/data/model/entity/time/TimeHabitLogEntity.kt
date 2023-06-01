package com.bibbidi.habittracker.data.model.entity.time

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.threeten.bp.Duration
import org.threeten.bp.LocalDate

@Entity(
    tableName = "time_habit_logs",
    foreignKeys = [
        ForeignKey(
            entity = TimeHabitEntity::class,
            parentColumns = ["time_habit_id"],
            childColumns = ["time_habit_parent_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TimeHabitLogEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "time_habit_log_id")
    val timeHabitLogId: Long? = null,
    @ColumnInfo(name = "time_habit_parent_id")
    val timeHabitId: Long? = null,
    val date: LocalDate = LocalDate.now(),
    val duration: Duration = Duration.ofHours(0L)
)
