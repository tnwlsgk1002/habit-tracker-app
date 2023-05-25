package com.bibbidi.habittracker.ui.customview

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.ui.model.DayOfTheWeekUiModel
import com.bibbidi.habittracker.utils.getBasicTextColor
import com.bibbidi.habittracker.utils.getOnPrimaryColor
import com.bibbidi.habittracker.utils.getPrimaryColor

class DateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val tvDayOfTheWeek: TextView
    private val tvDayOfTheMonth: TextView
    private val bgDayOfTheMonth: GradientDrawable

    var isToday: Boolean = false
        set(value) {
            field = value

            tvDayOfTheMonth.typeface = ResourcesCompat.getFont(
                context,
                when (value) {
                    true -> R.font.pretendard_bold
                    false -> R.font.pretendard_semibold
                }
            )
            ResourcesCompat.getFont(context, R.font.pretendard_bold)
            invalidate()
        }

    var checked: Boolean = false
        set(value) {
            field = value
            bgDayOfTheMonth.setColor(
                when (value) {
                    true -> checkedBackgroundColor
                    false -> Color.TRANSPARENT
                }
            )

            val contentTextColor = when (value) {
                true -> checkedTextColor
                false -> notCheckedTextColor
            }

            tvDayOfTheMonth.setTextColor(contentTextColor)
            invalidate()
        }

    var dayOfTheWeek: DayOfTheWeekUiModel = DayOfTheWeekUiModel.SUN
        set(value) {
            field = value

            tvDayOfTheWeek.text = when (value) {
                DayOfTheWeekUiModel.SUN -> context.getString(R.string.sunday)
                DayOfTheWeekUiModel.MON -> context.getString(R.string.monday)
                DayOfTheWeekUiModel.TUE -> context.getString(R.string.tuesday)
                DayOfTheWeekUiModel.WED -> context.getString(R.string.wednesday)
                DayOfTheWeekUiModel.THU -> context.getString(R.string.thursday)
                DayOfTheWeekUiModel.FRI -> context.getString(R.string.friday)
                DayOfTheWeekUiModel.SAT -> context.getString(R.string.saturday)
            }
            invalidate()
        }

    var dayOfTheMonth: Int = 1
        set(value) {
            field = value
            tvDayOfTheMonth.text = value.toString()
            invalidate()
        }

    private var checkedBackgroundColor: Int = getPrimaryColor(context)
    private var checkedTextColor: Int = getOnPrimaryColor(context)
    private var notCheckedTextColor: Int = getBasicTextColor(context)

    init {
        val view = inflate(context, R.layout.customview_date_view, this)
        tvDayOfTheWeek = view.findViewById(R.id.tv_day_of_week)
        tvDayOfTheMonth = view.findViewById(R.id.tv_day_of_month)
        bgDayOfTheMonth = tvDayOfTheMonth.background as GradientDrawable

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.DateView,
            defStyleAttr,
            0
        )

        checkedBackgroundColor =
            typedArray.getColor(
                R.styleable.DateView_checkedBackgroundColor,
                checkedBackgroundColor
            )

        checkedTextColor =
            typedArray.getColor(R.styleable.DateView_checkedTextColor, checkedTextColor)
        notCheckedTextColor =
            typedArray.getColor(R.styleable.DateView_notCheckedTextColor, notCheckedTextColor)

        isToday = typedArray.getBoolean(R.styleable.DateView_isToday, false)
        checked = typedArray.getBoolean(R.styleable.DateView_checked, false)
        dayOfTheWeek = DayOfTheWeekUiModel.values()[
            typedArray.getInt(
                R.styleable.DateView_dayOfTheWeek,
                DayOfTheWeekUiModel.SUN.ordinal
            )
        ]
        dayOfTheMonth = typedArray.getInt(R.styleable.DateView_dayOfTheMonth, 1)
        typedArray.recycle()
    }
}
