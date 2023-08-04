package com.bibbidi.habittracker.di

import com.bibbidi.habittracker.data.source.HabitLogRepositoryImpl
import com.bibbidi.habittracker.data.source.HabitMemoRepositoryImpl
import com.bibbidi.habittracker.data.source.HabitRepositoryImpl
import com.bibbidi.habittracker.domain.repository.HabitLogRepository
import com.bibbidi.habittracker.domain.repository.HabitMemoRepository
import com.bibbidi.habittracker.domain.repository.HabitRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindHabitRepository(
        habitRepository: HabitRepositoryImpl
    ): HabitRepository

    @Singleton
    @Binds
    abstract fun bindHabitLogRepository(
        habitLogRepository: HabitLogRepositoryImpl
    ): HabitLogRepository

    @Singleton
    @Binds
    abstract fun bindHabitMemoRepository(
        habitMemoRepository: HabitMemoRepositoryImpl
    ): HabitMemoRepository
}
