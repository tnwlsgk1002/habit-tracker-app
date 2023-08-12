package com.bibbidi.habittracker.domain.usecase.habltlog

import com.bibbidi.habittracker.data.model.DBResult
import com.bibbidi.habittracker.domain.model.HabitWithLogs
import com.bibbidi.habittracker.domain.repository.HabitLogRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetHabitLogsByIdUseCase @Inject constructor(private val habitLogRepository: HabitLogRepository) {

    suspend operator fun invoke(habitId: Long?): Flow<DBResult<HabitWithLogs>> {
        return habitLogRepository.getHabitWithLogs(habitId)
    }
}
