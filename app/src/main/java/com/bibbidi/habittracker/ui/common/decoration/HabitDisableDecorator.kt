package com.bibbidi.habittracker.ui.common.decoration

import com.bibbidi.habittracker.ui.model.habit.HabitWithLogsUiModel
import com.bibbidi.habittracker.utils.asCalendarDay
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class HabitDisableDecorator(habitWithLogs: HabitWithLogsUiModel) : DayViewDecorator {

    private val startDate = habitWithLogs.habit.startDate

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return day?.isBefore(startDate.asCalendarDay()) ?: false
    }

    override fun decorate(view: DayViewFacade?) {
        view?.setDaysDisabled(true)
    }
}
