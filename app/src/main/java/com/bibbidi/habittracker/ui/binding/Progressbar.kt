package com.bibbidi.habittracker.ui.binding

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.bibbidi.habittracker.ui.common.UiState
import com.bibbidi.habittracker.ui.model.habit.HabitWithLogUiModel

@BindingAdapter("bind:item_visibility")
fun setItemVisibility(view: ProgressBar, itemList: UiState<List<HabitWithLogUiModel>>?) {
    if (itemList is UiState.Loading) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}
