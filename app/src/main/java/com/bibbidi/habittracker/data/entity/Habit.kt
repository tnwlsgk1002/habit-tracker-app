package com.bibbidi.habittracker.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

@Entity
data class Habit(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val order: Int,
    val name: String,
    val startDate: LocalDate,
    val emoji: String,
    val color: String,
    val alarmTime: LocalTime?,
    val whenRun: String = "",
    val repeatDayOfTheWeeks: Set<DayOfTheWeek> = DayOfTheWeek.values().toSet(),
    val timeFrame: Set<TimeFrame> = TimeFrame.values().toSet()
)
