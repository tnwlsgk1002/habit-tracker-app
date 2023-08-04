package com.bibbidi.habittracker.ui.detailhabit.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bibbidi.habittracker.ui.detailhabit.DetailViewModel
import com.bibbidi.habittracker.ui.detailhabit.fragment.HabitMemosFragment
import com.bibbidi.habittracker.ui.detailhabit.fragment.HabitStatisticsFragment

class HabitResultAdapter(fragment: Fragment, private val viewModel: DetailViewModel) :
    FragmentStateAdapter(fragment) {

    companion object {
        const val ITEM_COUNT = 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HabitStatisticsFragment().apply { sharedViewModel = viewModel }
            1 -> HabitMemosFragment().apply { sharedViewModel = viewModel }
            else -> error("HabitResultAdapter's itemCount is $itemCount")
        }
    }

    override fun getItemCount(): Int = ITEM_COUNT
}
