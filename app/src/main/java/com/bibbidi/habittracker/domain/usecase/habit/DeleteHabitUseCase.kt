package com.bibbidi.habittracker.domain.usecase.habit

import com.bibbidi.habittracker.domain.AlarmHelper
import com.bibbidi.habittracker.domain.repository.HabitRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteHabitUseCase @Inject constructor(
    private val alarmHelper: AlarmHelper,
    private val habitRepository: HabitRepository
) {

    suspend operator fun invoke(habitId: Long?) {
        habitRepository.deleteHabitById(habitId)
        alarmHelper.cancelAlarm(habitId)
    }
}
