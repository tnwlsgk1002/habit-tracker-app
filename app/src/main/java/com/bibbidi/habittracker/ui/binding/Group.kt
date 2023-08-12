package com.bibbidi.habittracker.ui.binding

import android.view.View
import androidx.databinding.BindingAdapter
import com.bibbidi.habittracker.ui.common.UiState

@BindingAdapter("bind:item_visibility")
fun setItemVisibility(view: View, item: UiState<Any>?) {
    if (item is UiState.Empty) {
        view.visibility = View.VISIBLE
    } else {
        view.visibility = View.GONE
    }
}
