package com.bibbidi.habittracker.data.model.entity.track

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate

@Entity(
    tableName = "track_habit_logs",
    foreignKeys = [
        ForeignKey(
            entity = TrackHabitEntity::class,
            parentColumns = ["track_habit_id"],
            childColumns = ["track_habit_parent_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TrackHabitLogEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("track_habit_log_id")
    val trackHabitLogId: Long? = null,
    @ColumnInfo("track_habit_parent_id")
    val trackHabitId: Long? = null,
    val date: LocalDate = LocalDate.now(),
    val value: Long? = null
)
