package com.bibbidi.habittracker.data.model.habit

data class DailyHabitLogs(
    val total: Int,
    val finishCount: Int,
    val logs: List<HabitLog>
) {
    companion object {
        fun createDailyHabitLogs(logs: List<HabitLog>): DailyHabitLogs {
            return DailyHabitLogs(logs.size, logs.count { it.isCompleted }, logs)
        }
    }
}
