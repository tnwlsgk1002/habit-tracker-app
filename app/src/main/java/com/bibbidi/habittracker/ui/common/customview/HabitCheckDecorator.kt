package com.bibbidi.habittracker.ui.common.customview

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.ui.model.habit.HabitWithLogsUiModel
import com.bibbidi.habittracker.utils.asCalendarDay
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade

class HabitCheckDecorator(context: Context, habitWithLogs: HabitWithLogsUiModel) : DayViewDecorator {

    private val logMap = habitWithLogs.habitLogs.map { (k, v) -> k.asCalendarDay() to v }.toMap()

    val drawable: Drawable? = ContextCompat.getDrawable(context, R.drawable.inset_calendar_day)

    override fun shouldDecorate(day: CalendarDay?): Boolean {
        return logMap[day]?.isCompleted ?: false
    }

    override fun decorate(view: DayViewFacade?) {
        drawable ?: return
        view?.setBackgroundDrawable(drawable)
    }
}
