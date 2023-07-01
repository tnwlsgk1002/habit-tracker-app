package com.bibbidi.habittracker.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.ItemHabitCheckBinding
import com.bibbidi.habittracker.databinding.ItemHabitTimeBinding
import com.bibbidi.habittracker.databinding.ItemHabitTrackBinding
import com.bibbidi.habittracker.ui.common.BaseViewHolder
import com.bibbidi.habittracker.ui.common.viewBinding
import com.bibbidi.habittracker.ui.model.habit.log.CheckHabitLogUiModel
import com.bibbidi.habittracker.ui.model.habit.log.HabitLogUiModel
import com.bibbidi.habittracker.ui.model.habit.log.TimeHabitLogUiModel
import com.bibbidi.habittracker.ui.model.habit.log.TrackHabitLogUiModel
import com.bibbidi.habittracker.utils.toGoalTimeString

class HabitsAdapter(
    private val onCheckBox: (CheckHabitLogUiModel, Boolean) -> Unit,
    private val onTurnStopWatch: (TimeHabitLogUiModel, Boolean) -> Unit,
    private val onClickRecordButton: (TrackHabitLogUiModel) -> Unit,
    private val onClickMenu: (HabitLogUiModel, View) -> Unit
) :
    ListAdapter<HabitLogUiModel, BaseViewHolder<out HabitLogUiModel, out ViewBinding>>(
        HabitItemsDiffCallback
    ) {

    class CheckHabitItemViewHolder(
        private val binding: ItemHabitCheckBinding,
        private val onCheck: (CheckHabitLogUiModel, Boolean) -> Unit,
        private val onClickMenu: (CheckHabitLogUiModel, View) -> Unit
    ) : BaseViewHolder<CheckHabitLogUiModel, ItemHabitCheckBinding>(binding) {

        private var checkItem: CheckHabitLogUiModel? = null

        init {
            with(binding) {
                checkbox.setOnCheckedChangeListener { _, isChecked ->
                    checkItem?.let { item -> onCheck(item, isChecked) }
                }

                ibMenu.setOnClickListener { view ->
                    checkItem?.let { item -> onClickMenu(item, view) }
                }
            }
        }

        override fun bind(item: CheckHabitLogUiModel) {
            with(binding) {
                checkItem = item
                tvEmoji.text = item.emoji
                tvHabitTitle.text = item.name
                tvWhen.text = item.whenRun
                groupAlarm.visibility = when (item.alarmTime != null) {
                    true -> View.VISIBLE
                    false -> View.INVISIBLE
                }
                checkbox.isChecked = item.isChecked
            }
        }
    }

    class TimeHabitItemViewHolder(
        private val binding: ItemHabitTimeBinding,
        private val onTurn: (TimeHabitLogUiModel, Boolean) -> Unit,
        private val onClickMenu: (TimeHabitLogUiModel, View) -> Unit
    ) : BaseViewHolder<TimeHabitLogUiModel, ItemHabitTimeBinding>(binding) {

        private var timeItem: TimeHabitLogUiModel? = null

        init {
            with(binding) {
                checkboxPlay.setOnCheckedChangeListener { _, isChecked ->
                    timeItem?.let { onTurn(it, isChecked) }
                }
                ibMenu.setOnClickListener { view ->
                    timeItem?.let { onClickMenu(it, view) }
                }
            }
        }

        override fun bind(item: TimeHabitLogUiModel) {
            with(binding) {
                timeItem = item
                tvEmoji.text = item.emoji
                tvHabitTitle.text = item.name
                tvWhen.text = item.whenRun
                tvGoal.text = item.goalDuration.toGoalTimeString()
                tvCntTime.text = item.cntDuration.toString()
                groupAlarm.visibility = when (item.alarmTime != null) {
                    true -> View.VISIBLE
                    false -> View.INVISIBLE
                }
            }
        }
    }

    class TrackHabitItemViewHolder(
        private val binding: ItemHabitTrackBinding,
        private val onClickRecordButton: (TrackHabitLogUiModel) -> Unit,
        private val onClickMenu: (TrackHabitLogUiModel, View) -> Unit
    ) : BaseViewHolder<TrackHabitLogUiModel, ItemHabitTrackBinding>(binding) {

        private var trackItem: TrackHabitLogUiModel? = null

        init {
            with(binding) {
                buttonLog.setOnClickListener {
                    trackItem?.let { onClickRecordButton(it) }
                }
                ibMenu.setOnClickListener { view ->
                    trackItem?.let { onClickMenu(it, view) }
                }
            }
        }

        override fun bind(item: TrackHabitLogUiModel) {
            with(binding) {
                trackItem = item
                tvEmoji.text = item.emoji
                tvHabitTitle.text = item.name
                tvWhen.text = item.whenRun
                groupAlarm.visibility = when (item.alarmTime != null) {
                    true -> View.VISIBLE
                    false -> View.INVISIBLE
                }
                buttonLog.text =
                    item.value?.toString() ?: itemView.context.getString(R.string.record)

                buttonLog.icon = if (item.value == null) {
                    ContextCompat.getDrawable(itemView.context, R.drawable.ic_thick_plus)
                } else {
                    null
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
            is CheckHabitLogUiModel -> CHECK_TYPE
            is TimeHabitLogUiModel -> TIME_TYPE
            is TrackHabitLogUiModel -> TRACK_TYPE
        }
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<out HabitLogUiModel, out ViewBinding>,
        position: Int
    ) {
        val item = getItem(position)
        when (holder) {
            is CheckHabitItemViewHolder -> holder.bind(item as CheckHabitLogUiModel)
            is TimeHabitItemViewHolder -> holder.bind(item as TimeHabitLogUiModel)
            is TrackHabitItemViewHolder -> holder.bind(item as TrackHabitLogUiModel)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<out HabitLogUiModel, out ViewBinding> {
        return when (viewType) {
            CHECK_TYPE -> CheckHabitItemViewHolder(
                parent.viewBinding(ItemHabitCheckBinding::inflate),
                onCheckBox,
                onClickMenu
            )
            TIME_TYPE -> TimeHabitItemViewHolder(
                parent.viewBinding(ItemHabitTimeBinding::inflate),
                onTurnStopWatch,
                onClickMenu
            )
            TRACK_TYPE -> TrackHabitItemViewHolder(
                parent.viewBinding(ItemHabitTrackBinding::inflate),
                onClickRecordButton,
                onClickMenu
            )
            else -> throw IllegalArgumentException("is not exist viewHolder type")
        }
    }

    object HabitItemsDiffCallback : DiffUtil.ItemCallback<HabitLogUiModel>() {
        override fun areItemsTheSame(oldItem: HabitLogUiModel, newItem: HabitLogUiModel): Boolean {
            return oldItem.logId == newItem.logId
        }

        override fun areContentsTheSame(
            oldItem: HabitLogUiModel,
            newItem: HabitLogUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}
