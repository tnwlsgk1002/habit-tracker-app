package com.bibbidi.habittracker.domain.usecase.habitresult

import com.bibbidi.habittracker.data.model.DBResult
import com.bibbidi.habittracker.domain.model.HabitWithLog
import com.bibbidi.habittracker.domain.model.Progress
import com.bibbidi.habittracker.domain.repository.HabitLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.threeten.bp.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetHabitProgressByDateUseCase @Inject constructor(private val habitLogRepository: HabitLogRepository) {

    private fun List<HabitWithLog>.getProgress(): Progress {
        return Progress(size, count { it.habitLog.isCompleted })
    }

    suspend operator fun invoke(date: LocalDate): Flow<DBResult<Progress>> {
        return habitLogRepository.getAllHabitWithLogByDate(date).map {
            when (it) {
                is DBResult.Success -> DBResult.Success(it.data.getProgress())
                is DBResult.Loading -> DBResult.Loading
                is DBResult.Error -> DBResult.Error(it.exception)
                is DBResult.Empty -> DBResult.Empty
            }
        }
    }
}
