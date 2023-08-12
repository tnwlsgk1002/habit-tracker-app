package com.bibbidi.habittracker.di

import android.content.Context
import com.bibbidi.habittracker.data.source.database.HabitsDao
import com.bibbidi.habittracker.data.source.database.HabitsTrackerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDataBase(
        @ApplicationContext context: Context,
        scope: CoroutineScope
    ): HabitsTrackerDatabase {
        return HabitsTrackerDatabase.getInstance(context, scope)
    }

    @Provides
    fun provideHabitsDao(appDatabase: HabitsTrackerDatabase): HabitsDao {
        return appDatabase.habitDao()
    }
}
