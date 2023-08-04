package com.bibbidi.habittracker.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bibbidi.habittracker.databinding.ItemHabitLogBinding
import com.bibbidi.habittracker.ui.common.BaseViewHolder
import com.bibbidi.habittracker.ui.common.delegate.viewBinding
import com.bibbidi.habittracker.ui.model.habit.HabitWithLogUiModel

class HabitsAdapter(
    private val onClick: (View, HabitWithLogUiModel) -> Unit,
    private val onCheck: (HabitWithLogUiModel, Boolean) -> Unit,
    private val onClickMenu: (HabitWithLogUiModel, View) -> Unit
) :
    ListAdapter<HabitWithLogUiModel, HabitsAdapter.HabitItemViewHolder>(
        HabitItemsDiffCallback
    ) {

    class HabitItemViewHolder(
        private val binding: ItemHabitLogBinding,
        private val onClick: (View, HabitWithLogUiModel) -> Unit,
        private val onCheck: (HabitWithLogUiModel, Boolean) -> Unit,
        private val onClickMenu: (HabitWithLogUiModel, View) -> Unit
    ) : BaseViewHolder<HabitWithLogUiModel, ItemHabitLogBinding>(binding) {

        private var checkItem: HabitWithLogUiModel? = null

        init {
            binding.run {
                ibMenu.setOnClickListener { view ->
                    checkItem?.let { item -> onClickMenu(item, view) }
                }
                binding.containerHabitLog.setOnClickListener {
                    checkItem?.let { item -> onClick(it, item) }
                }
            }
        }

        override fun bind(item: HabitWithLogUiModel) {
            binding.run {
                checkItem = item
                habitWithLog = item
                binding.executePendingBindings()
                checkbox.setOnCheckedChangeListener { _, isChecked ->
                    checkItem?.let { item -> onCheck(item, isChecked) }
                }
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
            onClick,
            onCheck,
            onClickMenu
        )
    }

    object HabitItemsDiffCallback : DiffUtil.ItemCallback<HabitWithLogUiModel>() {
        override fun areItemsTheSame(oldItem: HabitWithLogUiModel, newItem: HabitWithLogUiModel): Boolean {
            return oldItem.habit.id == newItem.habit.id
        }

        override fun areContentsTheSame(
            oldItem: HabitWithLogUiModel,
            newItem: HabitWithLogUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}
