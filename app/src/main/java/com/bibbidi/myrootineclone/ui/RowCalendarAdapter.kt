package com.bibbidi.myrootineclone.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bibbidi.myrootineclone.databinding.ItemRowCalendarBinding
import com.bibbidi.myrootineclone.ui.customview.DateView

class RowCalendarAdapter(private val onClick: (DateItem) -> (Unit)) : ListAdapter<Array<DateItem>, RowCalendarAdapter.DateItemViewHolder>(DateViewsDiffCallback) {

    override fun onBindViewHolder(holder: DateItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateItemViewHolder {
        val binding = ItemRowCalendarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DateItemViewHolder(binding, onClick)
    }

    class DateItemViewHolder(binding: ItemRowCalendarBinding, private val onClick: (DateItem) -> Unit) : ViewHolder(binding.root) {

        private val dateViews: Array<DateView>
        private var clickedDateViewIndex: Int = 0

        init {
            with(binding) {
                dateViews = arrayOf(
                    dateViewSun,
                    dateViewMon,
                    dateViewTue,
                    dateViewWed,
                    dateViewThu,
                    dateViewFri,
                    dateViewSat
                )
            }
        }

        fun bind(items: Array<DateItem>) {
            items.forEachIndexed { index, item ->
                dateViews[index].apply {
                    checked = item.checked
                    dateState = item.dateState
                    dayOfTheMonth = item.dayOfTheMonth
                    dayOfTheWeek = item.dayOfTheWeek
                    isToday = item.isToday

                    if (checked) {
                        clickedDateViewIndex = index
                    }

                    setOnClickListener {
                        dateViews[index].checked = true
                        dateViews[clickedDateViewIndex].checked = false
                        clickedDateViewIndex = index
                        onClick(item)
                    }
                }
            }
        }
    }

    object DateViewsDiffCallback : DiffUtil.ItemCallback<Array<DateItem>>() {
        override fun areItemsTheSame(oldItem: Array<DateItem>, newItem: Array<DateItem>): Boolean {
            val oldDateItems = oldItem.map { it.dayOfTheMonth }
            val newDateItems = newItem.map { it.dayOfTheMonth }
            return oldDateItems == newDateItems
        }

        override fun areContentsTheSame(
            oldItem: Array<DateItem>,
            newItem: Array<DateItem>
        ): Boolean {
            return oldItem.contentEquals(newItem)
        }
    }
}