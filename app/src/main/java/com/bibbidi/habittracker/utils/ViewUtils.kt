package com.bibbidi.habittracker.utils

import android.annotation.SuppressLint
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.NumberPicker
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import java.lang.reflect.InvocationTargetException

fun View.getBackgroundColor(): Int? {
    return (background as? ColorDrawable)?.color
}

@SuppressLint("DiscouragedPrivateApi")
fun NumberPicker.animateChange(isIncrement: Boolean) {
    post {
        try {
            javaClass.getDeclaredMethod("changeValueByOne", Boolean::class.javaPrimitiveType)
                .also { function ->
                    function.isAccessible = true
                    function.invoke(this, isIncrement)
                }
        } catch (e: NoSuchMethodException) {
            Log.e(javaClass.simpleName, e.stackTraceToString())
        } catch (e: IllegalAccessException) {
            Log.e(javaClass.simpleName, e.stackTraceToString())
        } catch (e: IllegalArgumentException) {
            Log.e(javaClass.simpleName, e.stackTraceToString())
        } catch (e: InvocationTargetException) {
            Log.e(javaClass.simpleName, e.stackTraceToString())
        }
    }
}

fun showMenu(view: View, @MenuRes menuRes: Int, onMenuItemClick: (MenuItem) -> Boolean) {
    val popup = PopupMenu(view.context, view)
    popup.menuInflater.inflate(menuRes, popup.menu)
    popup.setOnMenuItemClickListener { menuItem ->
        onMenuItemClick(menuItem)
    }

    popup.show()
}
