package com.bibbidi.habittracker.di

import com.bibbidi.habittracker.data.source.DefaultHabitsRepository
import com.bibbidi.habittracker.domain.HabitsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindHabitsRepository(
        defaultHabitsRepository: DefaultHabitsRepository
    ): HabitsRepository
}
