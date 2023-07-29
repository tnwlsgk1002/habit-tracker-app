package com.bibbidi.habittracker.data.model.habit

data class DailyHabitLogs(
    val total: Int,
    val finishCount: Int,
    val logs: List<HabitWithLog>
) {
    companion object {
        fun createDailyHabitLogs(logs: List<HabitWithLog>): DailyHabitLogs {
            return DailyHabitLogs(logs.size, logs.count { it.habitLog.isCompleted }, logs)
        }
    }
}
