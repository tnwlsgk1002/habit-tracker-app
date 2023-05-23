package com.bibbidi.habittracker.ui.home.habits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.ItemHabitCheckBinding
import com.bibbidi.habittracker.databinding.ItemHabitTimeBinding
import com.bibbidi.habittracker.databinding.ItemHabitTrackBinding
import com.bibbidi.habittracker.ui.BaseViewHolder
import com.bibbidi.habittracker.utils.toGoalTimeString

class HabitsAdapter(
    private val onCheckBox: (CheckHabitItem, Boolean) -> Unit,
    private val onTurnStopWatch: (TimeHabitItem, Boolean) -> Unit,
    private val onClickRecordButton: (TrackHabitItem) -> Unit,
    private val onClickMenu: (HabitItem, View) -> Unit
) :
    ListAdapter<HabitItem, BaseViewHolder<out HabitItem, out ViewBinding>>(
        HabitItemsDiffCallback
    ) {

    class CheckHabitItemViewHolder(
        private val binding: ItemHabitCheckBinding,
        private val onCheck: (CheckHabitItem, Boolean) -> Unit,
        private val onClickMenu: (CheckHabitItem, View) -> Unit
    ) : BaseViewHolder<CheckHabitItem, ItemHabitCheckBinding>(binding) {

        override fun bind(item: CheckHabitItem) {
            with(binding) {
                tvEmoji.text = item.emoji
                tvHabitTitle.text = item.name
                tvWhen.text = item.whenRun
                groupAlarm.visibility = when (item.isAlarm) {
                    true -> View.VISIBLE
                    false -> View.INVISIBLE
                }
                checkbox.isChecked = item.isChecked

                checkbox.setOnCheckedChangeListener { _, isChecked ->
                    onCheck(item, isChecked)
                }
                ibMenu.setOnClickListener {
                    onClickMenu(item, it)
                }
            }
        }
    }

    class TimeHabitItemViewHolder(
        private val binding: ItemHabitTimeBinding,
        private val onTurn: (TimeHabitItem, Boolean) -> Unit,
        private val onClickMenu: (TimeHabitItem, View) -> Unit
    ) : BaseViewHolder<TimeHabitItem, ItemHabitTimeBinding>(binding) {

        override fun bind(item: TimeHabitItem) {
            with(binding) {
                tvEmoji.text = item.emoji
                tvHabitTitle.text = item.name
                tvWhen.text = item.whenRun
                tvGoal.text = item.goalDuration.toGoalTimeString()
                tvCntTime.text = item.cntDuration.toString()
                groupAlarm.visibility = when (item.isAlarm) {
                    true -> View.VISIBLE
                    false -> View.INVISIBLE
                }
                checkboxPlay.setOnCheckedChangeListener { _, isChecked ->
                    onTurn(item, isChecked)
                }
                ibMenu.setOnClickListener {
                    onClickMenu(item, it)
                }
            }
        }
    }

    class TrackHabitItemViewHolder(
        private val binding: ItemHabitTrackBinding,
        private val onClickRecordButton: (TrackHabitItem) -> Unit,
        private val onClickMenu: (TrackHabitItem, View) -> Unit
    ) : BaseViewHolder<TrackHabitItem, ItemHabitTrackBinding>(binding) {

        override fun bind(item: TrackHabitItem) {
            with(binding) {
                tvEmoji.text = item.emoji
                tvHabitTitle.text = item.name
                tvWhen.text = item.whenRun
                groupAlarm.visibility = when (item.isAlarm) {
                    true -> View.VISIBLE
                    false -> View.INVISIBLE
                }
                buttonLog.text =
                    item.value?.toString() ?: itemView.context.getString(R.string.record)
                buttonLog.setOnClickListener {
                    onClickRecordButton(item)
                }
                ibMenu.setOnClickListener {
                    onClickMenu(item, it)
                }
            }
        }
    }

    companion object {
        const val CHECK_TYPE = 1
        const val TIME_TYPE = 2
        const val TRACK_TYPE = 3
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is CheckHabitItem -> CHECK_TYPE
            is TimeHabitItem -> TIME_TYPE
            is TrackHabitItem -> TRACK_TYPE
        }
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<out HabitItem, out ViewBinding>,
        position: Int
    ) {
        val item = getItem(position)
        when (holder) {
            is CheckHabitItemViewHolder -> holder.bind(item as CheckHabitItem)
            is TimeHabitItemViewHolder -> holder.bind(item as TimeHabitItem)
            is TrackHabitItemViewHolder -> holder.bind(item as TrackHabitItem)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<out HabitItem, out ViewBinding> {
        return when (viewType) {
            CHECK_TYPE -> CheckHabitItemViewHolder(
                ItemHabitCheckBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                ),
                onCheckBox,
                onClickMenu
            )
            TIME_TYPE -> TimeHabitItemViewHolder(
                ItemHabitTimeBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                ),
                onTurnStopWatch,
                onClickMenu
            )
            TRACK_TYPE -> TrackHabitItemViewHolder(
                ItemHabitTrackBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                ),
                onClickRecordButton,
                onClickMenu
            )
            else -> throw IllegalArgumentException("is not exist viewHolder type")
        }
    }

    object HabitItemsDiffCallback : DiffUtil.ItemCallback<HabitItem>() {
        override fun areItemsTheSame(oldItem: HabitItem, newItem: HabitItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: HabitItem, newItem: HabitItem): Boolean {
            return oldItem == newItem
        }
    }
}
