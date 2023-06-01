package com.bibbidi.habittracker.ui.common.customview

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bibbidi.habittracker.R
import com.google.android.material.card.MaterialCardView

class HabitTypeSelectView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int = 0
) : MaterialCardView(context, attrs, defStyleAttr) {

    private val ivIcon: ImageView
    private val tvTitle: TextView
    private val tvDescription: TextView

    private var icon: Drawable? = null
        set(value) {
            field = value
            ivIcon.setImageDrawable(value)
            invalidate()
        }

    private var iconTint: Int = -1
        set(value) {
            field = value
            ivIcon.imageTintList = ColorStateList.valueOf(value)
            invalidate()
        }

    private var titleText: CharSequence = ""
        set(value) {
            field = value
            tvTitle.text = value
            invalidate()
        }

    private var descriptionText: CharSequence = ""
        set(value) {
            field = value
            tvDescription.text = value
            invalidate()
        }

    init {
        val view = inflate(context, R.layout.customview_select_habit_type, this)
        ivIcon = view.findViewById(R.id.iv_icon)
        tvTitle = view.findViewById(R.id.tv_title)
        tvDescription = view.findViewById(R.id.tv_description)

        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.HabitTypeSelectView,
            defStyleAttr,
            0
        )

        icon = typedArray.getDrawable(R.styleable.HabitTypeSelectView_habitTypeSelectView_icon)
        iconTint = typedArray.getColor(R.styleable.HabitTypeSelectView_habitTypeSelectView_iconTint, -1)
        titleText = typedArray.getText(R.styleable.HabitTypeSelectView_habitTypeSelectView_titleText)
        descriptionText = typedArray.getText(R.styleable.HabitTypeSelectView_habitTypeSelectView_descriptionText)
        typedArray.recycle()

        setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
    }
}
