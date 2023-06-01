package com.bibbidi.habittracker.data.source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bibbidi.habittracker.data.model.converters.DateConverters
import com.bibbidi.habittracker.data.model.converters.DayOfWeekConverter
import com.bibbidi.habittracker.data.model.converters.DurationConverter
import com.bibbidi.habittracker.data.model.entity.HabitEntity
import com.bibbidi.habittracker.data.model.entity.LongMemoInstanceEntity
import com.bibbidi.habittracker.data.model.entity.check.CheckHabitEntity
import com.bibbidi.habittracker.data.model.entity.check.CheckHabitLogEntity
import com.bibbidi.habittracker.data.model.entity.time.TimeHabitEntity
import com.bibbidi.habittracker.data.model.entity.time.TimeHabitLogEntity
import com.bibbidi.habittracker.data.model.entity.track.TrackHabitEntity
import com.bibbidi.habittracker.data.model.entity.track.TrackHabitLogEntity

@Database(
    entities = [
        HabitEntity::class,
        LongMemoInstanceEntity::class,
        CheckHabitEntity::class,
        CheckHabitLogEntity::class,
        TimeHabitEntity::class,
        TimeHabitLogEntity::class,
        TrackHabitEntity::class,
        TrackHabitLogEntity::class
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
