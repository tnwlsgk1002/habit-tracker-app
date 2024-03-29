package com.bibbidi.habittracker.ui.common.customview

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.utils.dayOfWeekValues
import com.bibbidi.habittracker.utils.getBasicTextColor
import com.bibbidi.habittracker.utils.getOnPrimaryColor
import com.bibbidi.habittracker.utils.getPrimaryColor
import com.bibbidi.habittracker.utils.getStringResource
import org.threeten.bp.DayOfWeek

class DateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    companion object {
        const val ABLE_ALPHA = 1F
        const val DISABLE_ALPHA = 0.5F
    }

    private val tvDayOfWeek: TextView
    private val tvDayOfTheMonth: TextView
    private val bgDayOfTheMonth: GradientDrawable

    var isToday: Boolean = false
        set(value) {
            tvDayOfTheMonth.alpha = if (value || checked) {
                ABLE_ALPHA
            } else {
                DISABLE_ALPHA
            }
            field = value
            invalidate()
        }

    var checked: Boolean = false
        set(value) {
            field = value
            bgDayOfTheMonth.setColor(
                if (value) {
                    checkedBackgroundColor
                } else {
                    Color.TRANSPARENT
                }
            )

            val contentTextColor = when (value) {
                true -> checkedTextColor
                false -> notCheckedTextColor
            }

            tvDayOfTheMonth.setTextColor(contentTextColor)
            tvDayOfTheMonth.alpha = if (value || isToday) {
                ABLE_ALPHA
            } else {
                DISABLE_ALPHA
            }
            invalidate()
        }

    var dayOfWeek: DayOfWeek = DayOfWeek.SUNDAY
        set(value) {
            field = value
            tvDayOfWeek.text = value.getStringResource(context)
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
        tvDayOfWeek = view.findViewById(R.id.tv_day_of_week)
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
        dayOfWeek = dayOfWeekValues[
            typedArray.getInt(
                R.styleable.DateView_dayOfTheWeek,
                DayOfWeek.SUNDAY.ordinal
            )
        ]
        dayOfTheMonth = typedArray.getInt(R.styleable.DateView_dayOfTheMonth, 1)
        typedArray.recycle()
    }
}
