package com.bibbidi.habittracker.ui.detailhabit.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bibbidi.habittracker.databinding.ItemHabitMemoBinding
import com.bibbidi.habittracker.ui.common.BaseViewHolder
import com.bibbidi.habittracker.ui.common.delegate.viewBinding
import com.bibbidi.habittracker.ui.model.habit.HabitMemoItem

class HabitMemoAdapter(
    private val onClick: (HabitMemoItem) -> (Unit)
) :
    ListAdapter<HabitMemoItem, HabitMemoAdapter.HabitMemoViewHolder>(HabitMemosDiffCallback) {

    class HabitMemoViewHolder(
        private val binding: ItemHabitMemoBinding,
        private val onClick: (HabitMemoItem) -> Unit
    ) :
        BaseViewHolder<HabitMemoItem, ItemHabitMemoBinding>(binding) {

        private var memoItem: HabitMemoItem? = null

        init {
            binding.containerMemo.setOnClickListener {
                memoItem?.let(onClick)
            }
        }

        override fun bind(item: HabitMemoItem) {
            memoItem = item
            binding.item = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitMemoViewHolder {
        return HabitMemoViewHolder(parent.viewBinding(ItemHabitMemoBinding::inflate), onClick)
    }

    override fun onBindViewHolder(holder: HabitMemoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object HabitMemosDiffCallback : DiffUtil.ItemCallback<HabitMemoItem>() {
        override fun areItemsTheSame(oldItem: HabitMemoItem, newItem: HabitMemoItem): Boolean {
            return oldItem.logId == newItem.logId
        }

        override fun areContentsTheSame(
            oldItem: HabitMemoItem,
            newItem: HabitMemoItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}
