package com.bibbidi.habittracker.di

import com.bibbidi.habittracker.data.source.DefaultHabitsRepository
import com.bibbidi.habittracker.data.source.HabitsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindHabitsRepository(
        defaultHabitsRepository: DefaultHabitsRepository
    ): HabitsRepository
}
