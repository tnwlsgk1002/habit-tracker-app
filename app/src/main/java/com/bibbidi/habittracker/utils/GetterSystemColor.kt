package com.bibbidi.habittracker.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import com.google.android.material.color.MaterialColors

fun getPrimaryColor(context: Context): Int {
    val primaryColor = TypedValue()
    context.theme.resolveAttribute(android.R.attr.colorPrimary, primaryColor, true)
    return primaryColor.data
}

fun getOnPrimaryColor(context: Context): Int {
    val onPrimaryColor = TypedValue()
    context.theme.resolveAttribute(android.R.attr.textColorPrimary, onPrimaryColor, true)
    return onPrimaryColor.data
}

fun getBasicTextColor(context: Context): Int {
    val textColor = TypedValue()
    context.theme.resolveAttribute(android.R.attr.textColor, textColor, true)
    return textColor.data
}

fun getColorSurfaceVariant(view: View): Int {
    return MaterialColors.getColor(view, com.google.android.material.R.attr.colorSurfaceVariant)
}
