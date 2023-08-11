package com.bibbidi.habittracker.domain.usecase.habltlog

import com.bibbidi.habittracker.data.model.DBResult
import com.bibbidi.habittracker.domain.model.HabitWithLog
import com.bibbidi.habittracker.domain.model.TimeFilter
import com.bibbidi.habittracker.domain.repository.HabitLogRepository
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDate
import javax.inject.Inject

class GetFilteredHabitLogsByDateUseCase @Inject constructor(
    private val habitLogRepository: HabitLogRepository
) {

    suspend operator fun invoke(
        date: LocalDate,
        filter: TimeFilter
    ): Flow<DBResult<List<HabitWithLog>>> {
        return habitLogRepository.getFilteredHabitWithLogByDate(date, filter)
    }
}
