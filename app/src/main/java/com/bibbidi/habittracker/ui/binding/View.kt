package com.bibbidi.habittracker.ui.binding

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("bind:backgroundColor")
fun setBackgroundColor(view: View, colorString: String?) {
    colorString?.let {
        (view.background as GradientDrawable).setColor(Color.parseColor(colorString))
    }
}
