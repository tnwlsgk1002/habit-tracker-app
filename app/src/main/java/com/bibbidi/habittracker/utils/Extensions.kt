package com.bibbidi.habittracker.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import android.widget.NumberPicker
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.res.use
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bibbidi.habittracker.ui.model.ColorUiModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime

fun Long.asLocalDate(): LocalDate {
    val instance = Instant.ofEpochMilli(this)
    return instance.atZone(ZoneId.systemDefault()).toLocalDate()
}

fun LocalDate.asLong(): Long {
    val dateTime = atStartOfDay()
    val zoneDateTime = ZonedDateTime.of(dateTime, ZoneId.systemDefault())
    return zoneDateTime.toInstant().toEpochMilli()
}

fun LifecycleOwner.repeatOnStarted(block: suspend CoroutineScope.() -> Unit) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, block)
    }
}

fun View.getBackgroundColor(): Int? {
    return (background as? ColorDrawable)?.color
}

@ColorInt
@SuppressLint("Recycle")
fun Context.themeColor(
    @AttrRes themeAttrId: Int
): Int {
    return obtainStyledAttributes(
        intArrayOf(themeAttrId)
    ).use {
        it.getColor(0, Color.MAGENTA)
    }
}

fun ColorUiModel.asStateColor(): ColorStateList {
    val color = Color.parseColor(hexCode)
    return ColorStateList.valueOf(color)
}

@SuppressLint("DiscouragedPrivateApi")
fun NumberPicker.animateChange(isIncrement: Boolean) {
    post {
        try {
            javaClass.getDeclaredMethod("changeValueByOne", Boolean::class.javaPrimitiveType).also { function ->
                function.isAccessible = true
                function.invoke(this, isIncrement)
            }
        } catch (e: Exception) {
            Log.e(javaClass.simpleName, e.stackTraceToString())
        }
    }
}
