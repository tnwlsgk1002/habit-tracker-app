package com.bibbidi.habittracker.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bibbidi.habittracker.databinding.ItemHabitLogBinding
import com.bibbidi.habittracker.ui.common.BaseViewHolder
import com.bibbidi.habittracker.ui.common.delegate.viewBinding
import com.bibbidi.habittracker.ui.model.habit.HabitLogUiModel

class HabitsAdapter(
    private val onCheck: (HabitLogUiModel, Boolean) -> Unit,
    private val onClickMenu: (HabitLogUiModel, View) -> Unit
) :
    ListAdapter<HabitLogUiModel, HabitsAdapter.HabitItemViewHolder>(
        HabitItemsDiffCallback
    ) {

    class HabitItemViewHolder(
        private val binding: ItemHabitLogBinding,
        private val onCheck: (HabitLogUiModel, Boolean) -> Unit,
        private val onClickMenu: (HabitLogUiModel, View) -> Unit
    ) : BaseViewHolder<HabitLogUiModel, ItemHabitLogBinding>(binding) {

        private var checkItem: HabitLogUiModel? = null

        init {
            binding.run {
                checkbox.setOnCheckedChangeListener { _, isChecked ->
                    checkItem?.let { item -> onCheck(item, isChecked) }
                }
                ibMenu.setOnClickListener { view ->
                    checkItem?.let { item -> onClickMenu(item, view) }
                }
            }
        }

        override fun bind(item: HabitLogUiModel) {
            binding.run {
                checkItem = item
                habitLog = item
                binding.executePendingBindings()
            }
        }
    }

    override fun onBindViewHolder(holder: HabitItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HabitItemViewHolder {
        return HabitItemViewHolder(
            parent.viewBinding(ItemHabitLogBinding::inflate),
            onCheck,
            onClickMenu
        )
    }

    object HabitItemsDiffCallback : DiffUtil.ItemCallback<HabitLogUiModel>() {
        override fun areItemsTheSame(oldItem: HabitLogUiModel, newItem: HabitLogUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: HabitLogUiModel,
            newItem: HabitLogUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}
