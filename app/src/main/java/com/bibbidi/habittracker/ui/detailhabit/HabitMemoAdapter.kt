package com.bibbidi.habittracker.ui.detailhabit

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bibbidi.habittracker.databinding.ItemHabitMemoBinding
import com.bibbidi.habittracker.ui.common.BaseViewHolder
import com.bibbidi.habittracker.ui.common.delegate.viewBinding
import com.bibbidi.habittracker.ui.model.habit.HabitLogUiModel

class HabitMemoAdapter(
    private val onClick: (HabitLogUiModel) -> (Unit)
) :
    ListAdapter<HabitLogUiModel, HabitMemoAdapter.HabitMemoViewHolder>(HabitMemosDiffCallback) {

    class HabitMemoViewHolder(
        private val binding: ItemHabitMemoBinding,
        private val onClick: (HabitLogUiModel) -> Unit
    ) :
        BaseViewHolder<HabitLogUiModel, ItemHabitMemoBinding>(binding) {

        private var habitLogItem: HabitLogUiModel? = null

        init {
            binding.containerMemo.setOnClickListener {
                habitLogItem?.let(onClick)
            }
        }

        override fun bind(item: HabitLogUiModel) {
            habitLogItem = item
            binding.log = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitMemoViewHolder {
        return HabitMemoViewHolder(parent.viewBinding(ItemHabitMemoBinding::inflate), onClick)
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
