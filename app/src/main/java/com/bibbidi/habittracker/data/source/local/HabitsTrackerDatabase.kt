package com.bibbidi.habittracker.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bibbidi.habittracker.data.entity.Habit
import com.bibbidi.habittracker.data.entity.LongMemoInstance
import com.bibbidi.habittracker.data.entity.challenge.ChallengeHabit
import com.bibbidi.habittracker.data.entity.challenge.ChallengeHabitLog
import com.bibbidi.habittracker.data.entity.check.CheckHabit
import com.bibbidi.habittracker.data.entity.check.CheckHabitLog
import com.bibbidi.habittracker.data.entity.converters.DateConverters
import com.bibbidi.habittracker.data.entity.converters.DayOfTheWeekConverter
import com.bibbidi.habittracker.data.entity.converters.TimeFrameConverter
import com.bibbidi.habittracker.data.entity.time.TimeHabit
import com.bibbidi.habittracker.data.entity.time.TimeHabitLog
import com.bibbidi.habittracker.data.entity.track.TrackHabit
import com.bibbidi.habittracker.data.entity.track.TrackHabitLog

@Database(
    entities = [
        Habit::class,
        LongMemoInstance::class,
        ChallengeHabit::class,
        ChallengeHabitLog::class,
        CheckHabit::class,
        CheckHabitLog::class,
        TimeHabit::class,
        TimeHabitLog::class,
        TrackHabit::class,
        TrackHabitLog::class,
    ], version = 1, exportSchema = false
)
@TypeConverters(DateConverters::class, DayOfTheWeekConverter::class, TimeFrameConverter::class)
abstract class HabitsTrackerDatabase : RoomDatabase() {

    abstract fun habitDao(): HabitsDao
}
