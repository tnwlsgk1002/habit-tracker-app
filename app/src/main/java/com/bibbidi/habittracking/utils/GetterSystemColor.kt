package com.bibbidi.habittracking.utils

import android.content.Context
import android.util.TypedValue

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