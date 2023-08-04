package com.bibbidi.habittracker.ui.detailhabit.fragment

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.FragmentHabitStatisticsBinding
import com.bibbidi.habittracker.ui.common.delegate.viewBinding
import com.bibbidi.habittracker.ui.detailhabit.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HabitStatisticsFragment : Fragment(R.layout.fragment_habit_statistics) {

    lateinit var sharedViewModel: DetailViewModel

    private val binding by viewBinding(FragmentHabitStatisticsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBindingData()
    }

    private fun initBindingData() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = sharedViewModel
    }

    override fun onStart() {
        super.onStart()
        startAnimation()
    }

    private fun startAnimation() {
        val fadeInAnim = AnimationUtils.loadAnimation(context, R.anim.fade_in)
        val slideUpAnim = AnimationUtils.loadAnimation(context, R.anim.slide_up)
        val animationSet = AnimationSet(true).apply {
            addAnimation(fadeInAnim)
            addAnimation(slideUpAnim)
        }

        binding.run {
            containerCalendar.startAnimation(animationSet)
            containerCurrentAchievements.startAnimation(animationSet)
            containerBestAchievements.startAnimation(animationSet)
        }
    }
}
