package com.bibbidi.habittracker.ui.binding

import android.view.View
import androidx.constraintlayout.widget.Group
import androidx.databinding.BindingAdapter
import com.bibbidi.habittracker.ui.common.UiState
import com.bibbidi.habittracker.ui.model.habit.HabitLogUiModel

@BindingAdapter("bind:item_visibility")
fun setItemVisibility(view: Group, itemList: UiState<List<HabitLogUiModel>>?) {
    if (itemList is UiState.Empty) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}
