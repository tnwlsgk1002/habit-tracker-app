package com.bibbidi.habittracker.domain.usecase.habit

import com.bibbidi.habittracker.domain.AlarmHelper
import com.bibbidi.habittracker.domain.model.Habit
import com.bibbidi.habittracker.domain.repository.HabitRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateHabitUseCase @Inject constructor(
    private val alarmHelper: AlarmHelper,
    private val habitRepository: HabitRepository
) {

    suspend operator fun invoke(habit: Habit) {
        habitRepository.updateHabit(habit)
        alarmHelper.updateAlarm(habit)
    }
}
