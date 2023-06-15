package com.bibbidi.habittracker.ui.binding

import android.view.View
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.bibbidi.habittracker.ui.common.UiState
import com.bibbidi.habittracker.ui.model.habit.log.HabitLogUiModel

@BindingAdapter("bind:item_visibility")
fun setItemVisibility(view: ProgressBar, itemList: UiState<List<HabitLogUiModel>>?) {
    if (itemList is UiState.Loading) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}
