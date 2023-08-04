package com.bibbidi.habittracker.domain.usecase.habltlog

import com.bibbidi.habittracker.data.model.DBResult
import com.bibbidi.habittracker.domain.model.HabitWithLog
import com.bibbidi.habittracker.domain.repository.HabitLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.threeten.bp.LocalDate
import javax.inject.Inject

class GetHabitLogsByDateUseCase @Inject constructor(
    private val habitLogRepository: HabitLogRepository
) {

    suspend operator fun invoke(date: LocalDate): Flow<DBResult<List<HabitWithLog>>> {
        return habitLogRepository.getHabitWithLogsByDate(date).map { result ->
            if (result is DBResult.Success) {
                val data = result.data.sortedWith(compareBy({ it.habitLog.isCompleted }, { it.habit.id }))
                DBResult.Success(data)
            } else {
                result
            }
        }
    }
}
