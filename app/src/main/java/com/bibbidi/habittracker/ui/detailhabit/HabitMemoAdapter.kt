package com.bibbidi.habittracker.ui.detailhabit

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bibbidi.habittracker.databinding.ItemHabitMemoBinding
import com.bibbidi.habittracker.ui.common.BaseViewHolder
import com.bibbidi.habittracker.ui.common.delegate.viewBinding
import com.bibbidi.habittracker.ui.model.habit.HabitLogUiModel

class HabitMemoAdapter :
    ListAdapter<HabitLogUiModel, HabitMemoAdapter.HabitMemoViewHolder>(HabitMemosDiffCallback) {

    class HabitMemoViewHolder(val binding: ItemHabitMemoBinding) :
        BaseViewHolder<HabitLogUiModel, ItemHabitMemoBinding>(binding) {
        override fun bind(item: HabitLogUiModel) {
            binding.log = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitMemoViewHolder {
        return HabitMemoViewHolder(parent.viewBinding(ItemHabitMemoBinding::inflate))
    }

    override fun onBindViewHolder(holder: HabitMemoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object HabitMemosDiffCallback : DiffUtil.ItemCallback<HabitLogUiModel>() {
        override fun areItemsTheSame(oldItem: HabitLogUiModel, newItem: HabitLogUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: HabitLogUiModel,
            newItem: HabitLogUiModel
        ): Boolean {
            return oldItem.memo == newItem.memo
        }
    }
}
