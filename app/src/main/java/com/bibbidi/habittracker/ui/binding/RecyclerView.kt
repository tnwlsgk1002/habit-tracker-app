package com.bibbidi.habittracker.ui.binding

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bibbidi.habittracker.ui.common.Constants.HABIT_ITEM_PADDING
import com.bibbidi.habittracker.ui.common.UiState
import com.bibbidi.habittracker.ui.common.customview.ItemDecoration
import com.bibbidi.habittracker.ui.detailhabit.HabitMemoAdapter
import com.bibbidi.habittracker.ui.home.HabitsAdapter
import com.bibbidi.habittracker.ui.home.RowCalendarAdapter
import com.bibbidi.habittracker.ui.model.date.DateItem
import com.bibbidi.habittracker.ui.model.habit.HabitLogUiModel
import com.bibbidi.habittracker.ui.model.habit.HabitWithLogUiModel

@BindingAdapter("bind:adapter")
fun setAdapter(view: ViewPager2, adapter: RowCalendarAdapter) {
    view.adapter = adapter
}

@BindingAdapter("bind:adapter")
fun setAdapter(view: RecyclerView, adapter: HabitsAdapter) {
    view.adapter = adapter
    view.addItemDecoration(ItemDecoration(HABIT_ITEM_PADDING))
}

@BindingAdapter("bind:adapter")
fun setAdapter(view: RecyclerView, adapter: HabitMemoAdapter) {
    view.adapter = adapter
    view.addItemDecoration(ItemDecoration(HABIT_ITEM_PADDING))
}

@BindingAdapter("bind:itemList")
fun setItemList(view: ViewPager2, itemList: List<UiState<Array<DateItem>>>?) {
    (view.adapter as? RowCalendarAdapter)?.submitList(itemList)
}

@BindingAdapter("bind:logItemList")
fun setLogItemList(view: RecyclerView, itemList: UiState<List<HabitWithLogUiModel>>?) {
    if (itemList is UiState.Success) {
        view.visibility = View.VISIBLE
        (view.adapter as? HabitsAdapter)?.submitList(itemList.data)
    } else if (itemList is UiState.Empty) {
        view.visibility = View.GONE
    }
}

@BindingAdapter("bind:memoItemList")
fun setMemoItemList(view: RecyclerView, itemList: UiState<List<HabitLogUiModel>>?) {
    if (itemList is UiState.Success) {
        view.visibility = View.VISIBLE
        (view.adapter as? HabitMemoAdapter)?.submitList(itemList.data)
    } else if (itemList is UiState.Empty) {
        view.visibility = View.INVISIBLE
    }
}
