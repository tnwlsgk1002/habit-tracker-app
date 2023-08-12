package com.bibbidi.habittracker.domain.usecase.habitresult

import com.bibbidi.habittracker.data.model.DBResult
import com.bibbidi.habittracker.domain.model.HabitResult
import com.bibbidi.habittracker.domain.model.HabitWithLogs
import com.bibbidi.habittracker.domain.repository.HabitLogRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.threeten.bp.LocalDate
import org.threeten.bp.temporal.TemporalAdjusters
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.math.max

@Singleton
class GetHabitResultUseCase @Inject constructor(private val habitLogRepository: HabitLogRepository) {

    suspend operator fun invoke(habitId: Long?, standard: LocalDate): Flow<DBResult<HabitResult>> {
        return habitLogRepository.getHabitWithLogs(habitId).map {
            when (it) {
                is DBResult.Success -> DBResult.Success(it.data.getResult(standard))
                is DBResult.Loading -> DBResult.Loading
                is DBResult.Error -> DBResult.Error(it.exception)
                is DBResult.Empty -> DBResult.Empty
            }
        }
    }

    private fun HabitWithLogs.getResult(standard: LocalDate): HabitResult {
        val completedDate =
            habitLogs.filter { (_, v) -> v.isCompleted && !v.date.isAfter(standard) }.toList()
                .sortedBy { it.first }
                .map { it.first }

        if (completedDate.isEmpty()) return HabitResult(0, 0)

        val repeatDayOfWeeks = habit.repeatsDayOfTheWeeks.toList().sorted()
        var bestNumber = 0
        var cntNumber = 0
        var nextDate = completedDate.first()

        completedDate.forEach { d ->
            val index = (repeatDayOfWeeks.indexOf(d.dayOfWeek) + 1) % repeatDayOfWeeks.size
            cntNumber = if (nextDate.isEqual(d)) cntNumber + 1 else 1
            nextDate = d.with(TemporalAdjusters.next(repeatDayOfWeeks[index]))
            bestNumber = max(bestNumber, cntNumber)
        }

        if (completedDate.last().isBefore(standard) &&
            habit.repeatsDayOfTheWeeks.contains(standard.dayOfWeek)
        ) {
            cntNumber = 0
        }

        return HabitResult(cntNumber, bestNumber)
    }
}
