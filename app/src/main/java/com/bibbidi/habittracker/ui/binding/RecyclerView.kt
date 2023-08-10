package com.bibbidi.habittracker.ui.binding

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bibbidi.habittracker.ui.common.UiState
import com.bibbidi.habittracker.ui.common.decoration.PaddingDecoration
import com.bibbidi.habittracker.ui.common.dialog.colorpicker.ColorsAdapter
import com.bibbidi.habittracker.ui.detailhabit.adapter.HabitMemoAdapter
import com.bibbidi.habittracker.ui.home.HabitsAdapter
import com.bibbidi.habittracker.ui.home.RowCalendarAdapter
import com.bibbidi.habittracker.ui.model.ColorItem
import com.bibbidi.habittracker.ui.model.date.DateItem
import com.bibbidi.habittracker.ui.model.habit.HabitMemoItem
import com.bibbidi.habittracker.ui.model.habit.HabitWithLogUiModel
import com.bibbidi.habittracker.utils.Constants.HABIT_ITEM_PADDING

@BindingAdapter("bind:adapter")
fun setAdapter(view: ViewPager2, adapter: RowCalendarAdapter) {
    view.adapter = adapter
}

@BindingAdapter("bind:adapter")
fun setAdapter(view: RecyclerView, adapter: HabitsAdapter) {
    view.adapter = adapter
    view.addItemDecoration(PaddingDecoration(HABIT_ITEM_PADDING))
}

@BindingAdapter("bind:adapter")
fun setAdapter(view: RecyclerView, adapter: HabitMemoAdapter) {
    view.adapter = adapter
}

@BindingAdapter("bind:adapter")
fun setAdapter(view: RecyclerView, adapter: ColorsAdapter) {
    view.adapter = adapter
    view.addItemDecoration(PaddingDecoration(HABIT_ITEM_PADDING))
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
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("bind:memoItemList")
fun setMemoItemList(view: RecyclerView, itemList: UiState<List<HabitMemoItem>>?) {
    if (itemList is UiState.Success) {
        view.visibility = View.VISIBLE
        (view.adapter as? HabitMemoAdapter)?.submitList(itemList.data)
    } else {
        view.visibility = View.GONE
    }
}

@BindingAdapter("bind:colorItemList")
fun setColorItemList(view: RecyclerView, itemList: List<ColorItem>?) {
    (view.adapter as? ColorsAdapter)?.submitList(itemList)
}
