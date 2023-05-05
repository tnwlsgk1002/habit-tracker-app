package com.bibbidi.habittracker.ui.home.habits

data class CheckHabitItem(
    override val id: Long,
    override val order: Int,
    override val emoji: String,
    override val name: String,
    override val isAlarm: Boolean,
    override val whenRun: String,
    val isChecked: Boolean
) : HabitItem
