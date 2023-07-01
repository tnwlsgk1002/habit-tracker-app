package com.bibbidi.habittracker.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bibbidi.habittracker.databinding.ItemRowCalendarBinding
import com.bibbidi.habittracker.ui.common.BaseViewHolder
import com.bibbidi.habittracker.ui.common.UiState
import com.bibbidi.habittracker.ui.common.customview.DateView
import com.bibbidi.habittracker.ui.common.viewBinding
import com.bibbidi.habittracker.ui.model.date.DateItem
import com.facebook.shimmer.ShimmerFrameLayout

class RowCalendarAdapter(
    private val onClick: (DateItem) -> (Unit),
    itemRangeInserted: () -> Unit
) :
    ListAdapter<UiState<Array<DateItem>>, RowCalendarAdapter.DateItemViewHolder>(
        DateViewsDiffCallback
    ) {

    class DateItemViewHolder(
        binding: ItemRowCalendarBinding,
        private val onClick: (DateItem) -> Unit
    ) : BaseViewHolder<UiState<Array<DateItem>>, ItemRowCalendarBinding>(binding) {

        private val dateViews: Array<DateView>
        private var clickedDateViewIndex: Int = 0
        private val shimmerLayout: ShimmerFrameLayout
        private var cntItem: UiState<Array<DateItem>> = UiState.Loading

        init {
            with(binding) {
                shimmerLayout = shimmerFrameLayout.shimmerFrameLayout

                dateViews = arrayOf(
                    dateViewSun,
                    dateViewMon,
                    dateViewTue,
                    dateViewWed,
                    dateViewThu,
                    dateViewFri,
                    dateViewSat
                ).apply {
                    forEachIndexed { index, dateView ->
                        dateView.setOnClickListener {
                            val item = (cntItem as? UiState.Success)
                                ?: return@setOnClickListener
                            this[clickedDateViewIndex].checked = false
                            this[index].checked = true
                            clickedDateViewIndex = index
                            onClick(item.data[index])
                        }
                    }
                }
            }
        }

        override fun bind(item: UiState<Array<DateItem>>) {
            cntItem = item
            when (item) {
                is UiState.Success -> {
                    stopShimmer()
                    item.data.forEachIndexed { index, dateItem ->
                        dateViews[index].apply {
                            checked = dateItem.checked
                            dayOfTheMonth = dateItem.dayOfTheMonth
                            dayOfWeek = dateItem.dayOfWeek
                            isToday = dateItem.isToday

                            if (checked) {
                                clickedDateViewIndex = index
                            }
                        }
                    }
                }
                is UiState.Loading -> {
                    startShimmer()
                }
                else -> {}
            }
        }

        private fun startShimmer() {
            dateViews.forEach { it.visibility = View.INVISIBLE }
            shimmerLayout.visibility = View.VISIBLE
            shimmerLayout.startShimmer()
        }

        private fun stopShimmer() {
            dateViews.forEach { it.visibility = View.VISIBLE }
            shimmerLayout.visibility = View.INVISIBLE
            shimmerLayout.stopShimmer()
        }
    }

    private val adapterDataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            itemRangeInserted.invoke()
        }
    }

    init {
        registerAdapterDataObserver(adapterDataObserver)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateItemViewHolder {
        return DateItemViewHolder(parent.viewBinding(ItemRowCalendarBinding::inflate), onClick)
    }

    override fun onBindViewHolder(holder: DateItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    object DateViewsDiffCallback : DiffUtil.ItemCallback<UiState<Array<DateItem>>>() {
        override fun areItemsTheSame(
            oldItem: UiState<Array<DateItem>>,
            newItem: UiState<Array<DateItem>>
        ): Boolean {
            return when {
                oldItem is UiState.Success<Array<DateItem>> && newItem is UiState.Success<Array<DateItem>> -> {
                    oldItem.data.map { it.dayOfTheMonth } == newItem.data.map { it.dayOfTheMonth }
                }
                oldItem is UiState.Loading && newItem is UiState.Loading -> true
                else -> false
            }
        }

        override fun areContentsTheSame(
            oldItem: UiState<Array<DateItem>>,
            newItem: UiState<Array<DateItem>>
        ): Boolean {
            return oldItem.contentEquals(newItem)
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        unregisterAdapterDataObserver(adapterDataObserver)
        super.onDetachedFromRecyclerView(recyclerView)
    }
}
