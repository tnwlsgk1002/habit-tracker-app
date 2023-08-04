package com.bibbidi.habittracker.data.source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bibbidi.habittracker.data.model.converters.DateConverters
import com.bibbidi.habittracker.data.model.converters.DayOfWeekConverter
import com.bibbidi.habittracker.data.model.converters.DurationConverter
import com.bibbidi.habittracker.data.model.habit.entity.HabitEntity
import com.bibbidi.habittracker.data.model.habit.entity.HabitLogEntity

@Database(
    entities = [
        HabitEntity::class,
        HabitLogEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    DateConverters::class,
    DayOfWeekConverter::class,
    DurationConverter::class
)
abstract class HabitsTrackerDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitsDao
}
