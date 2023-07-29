package com.bibbidi.habittracker.ui.common.customview

import android.content.Context
import com.bibbidi.habittracker.ui.model.habit.HabitWithLogsUiModel
import com.bibbidi.habittracker.utils.asCalendarDay
import com.bibbidi.habittracker.utils.getPrimaryColor
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.spans.DotSpan

class HabitMemoDecorator(private val context: Context, habitWithLogs: HabitWithLogsUiModel) : DayViewDecorator {

    private val logMap = habitWithLogs.habitLogs.map { (k, v) -> k.asCalendarDay() to v }.toMap()

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return logMap[day]?.memo?.isNotBlank() ?: false
    }

    override fun decorate(view: DayViewFacade?) {
        view?.addSpan(DotSpan(4F, getPrimaryColor(context)))
    }
}
