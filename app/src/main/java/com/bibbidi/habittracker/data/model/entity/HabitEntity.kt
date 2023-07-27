package com.bibbidi.habittracker.data.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "habit_id")
    val id: Long?,
    val name: String,
    @ColumnInfo(name = "start_date")
    val startDate: LocalDate,
    val emoji: String,
    val alarmTime: LocalTime?,
    val repeatDayOfTheWeeks: Set<DayOfWeek> = DayOfWeek.values().toSet()
)
