package com.bibbidi.habittracker.domain.usecase.habit

import com.bibbidi.habittracker.domain.model.Habit
import com.bibbidi.habittracker.domain.repository.HabitRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetHabitUseCase @Inject constructor(private val habitRepository: HabitRepository) {

    suspend operator fun invoke(habitId: Long?): Habit {
        return habitRepository.getHabitById(habitId)
    }
}
