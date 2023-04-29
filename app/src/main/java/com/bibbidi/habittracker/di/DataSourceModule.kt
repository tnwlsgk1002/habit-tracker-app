package com.bibbidi.habittracker.di

import com.bibbidi.habittracker.data.source.HabitsDataSource
import com.bibbidi.habittracker.data.source.local.HabitsLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindHabitsLocalDataSource(
        habitsLocalDataSource: HabitsLocalDataSource
    ): HabitsDataSource
}
