package com.bibbidi.habittracker.di

import com.bibbidi.habittracker.data.source.AlarmHelper
import com.bibbidi.habittracker.ui.background.AlarmHelperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AlarmHelperModule {

    @Binds
    abstract fun bindAlarmHelper(
        alarmHelper: AlarmHelperImpl
    ): AlarmHelper
}
