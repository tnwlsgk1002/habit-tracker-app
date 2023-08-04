package com.bibbidi.habittracker.domain.usecase.habltlog

import com.bibbidi.habittracker.domain.model.HabitWithLog
import com.bibbidi.habittracker.domain.repository.HabitLogRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompleteHabitUseCase @Inject constructor(private val habitLogRepository: HabitLogRepository) {

    suspend operator fun invoke(log: HabitWithLog, isCompleted: Boolean) {
        habitLogRepository.insertHabitLog(log.copy(habitLog = log.habitLog.copy(isCompleted = isCompleted)))
    }
}
