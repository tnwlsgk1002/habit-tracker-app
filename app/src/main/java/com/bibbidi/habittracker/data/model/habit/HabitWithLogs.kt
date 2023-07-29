package com.bibbidi.habittracker.data.model.habit

import org.threeten.bp.LocalDate
import org.threeten.bp.temporal.TemporalAdjusters
import kotlin.math.max

data class HabitWithLogs(
    val habit: Habit,
    val habitLogs: Map<LocalDate, HabitLog> = mapOf()
) {
    data class HabitResult(
        val cntNumber: Int,
        val bestNumber: Int
    )

    fun getResult(date: LocalDate): HabitResult {
        val completedDate = habitLogs.filter { (d, v) -> v.isCompleted }.toList()
            .sortedBy { it.first }
            .map { it.first }

        if (completedDate.isEmpty()) return HabitResult(0, 0)

        val repeatDayOfWeeks = habit.repeatsDayOfTheWeeks.toList().sorted()
        var bestNumber = 0
        var cntNumber = 0
        var nextDate = completedDate.first()

        completedDate.forEach { d ->
            val index = repeatDayOfWeeks.indexOf(d.dayOfWeek) % repeatDayOfWeeks.size
            cntNumber = if (nextDate.isEqual(d)) cntNumber + 1 else 1
            nextDate = d.with(TemporalAdjusters.next(repeatDayOfWeeks[index]))
            bestNumber = max(bestNumber, cntNumber)
        }

        if (completedDate.none { d -> date == d }) cntNumber = 0

        return HabitResult(cntNumber, bestNumber)
    }
}
