package com.bibbidi.habittracker.utils

import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.annotation.MenuRes

fun showMenu(view: View, @MenuRes menuRes: Int, onMenuItemClick: (MenuItem) -> Boolean) {
    val popup = PopupMenu(view.context, view)
    popup.menuInflater.inflate(menuRes, popup.menu)
    popup.setOnMenuItemClickListener { menuItem ->
        onMenuItemClick(menuItem)
    }

    popup.show()
}
