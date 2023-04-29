package com.bibbidi.habittracker.di

import android.content.Context
import androidx.room.Room
import com.bibbidi.habittracker.data.source.local.HabitsDao
import com.bibbidi.habittracker.data.source.local.HabitsTrackerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context): HabitsTrackerDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            HabitsTrackerDatabase::class.java,
            "Habits.db"
        ).build()
    }

    @Provides
    fun provideHabitsDao(appDatabase: HabitsTrackerDatabase): HabitsDao {
        return appDatabase.habitDao()
    }
}
