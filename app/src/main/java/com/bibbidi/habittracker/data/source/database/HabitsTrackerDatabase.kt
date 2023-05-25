package com.bibbidi.habittracker.data.source.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bibbidi.habittracker.data.entity.HabitEntity
import com.bibbidi.habittracker.data.entity.LongMemoInstanceEntity
import com.bibbidi.habittracker.data.entity.check.CheckHabitEntity
import com.bibbidi.habittracker.data.entity.check.CheckHabitLogEntity
import com.bibbidi.habittracker.data.entity.converters.DateConverters
import com.bibbidi.habittracker.data.entity.converters.DayOfTheWeekConverter
import com.bibbidi.habittracker.data.entity.converters.DurationConverter
import com.bibbidi.habittracker.data.entity.converters.TimeFrameConverter
import com.bibbidi.habittracker.data.entity.time.TimeHabitEntity
import com.bibbidi.habittracker.data.entity.time.TimeHabitLogEntity
import com.bibbidi.habittracker.data.entity.track.TrackHabitEntity
import com.bibbidi.habittracker.data.entity.track.TrackHabitLogEntity

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
    DayOfTheWeekConverter::class,
    TimeFrameConverter::class,
    DurationConverter::class
)
abstract class HabitsTrackerDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitsDao
}
