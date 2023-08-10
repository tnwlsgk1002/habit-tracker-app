package com.bibbidi.habittracker.ui.detailhabit.fragment

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.FragmentHabitMemosBinding
import com.bibbidi.habittracker.ui.common.delegate.viewBinding
import com.bibbidi.habittracker.ui.common.dialog.memo.MemoBottomSheet
import com.bibbidi.habittracker.ui.detailhabit.DetailViewModel
import com.bibbidi.habittracker.ui.detailhabit.adapter.HabitMemoAdapter
import com.bibbidi.habittracker.ui.model.habit.HabitMemoItem
import com.bibbidi.habittracker.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HabitMemosFragment : Fragment(R.layout.fragment_habit_memos) {

    lateinit var sharedViewModel: DetailViewModel

    private val binding by viewBinding(FragmentHabitMemosBinding::bind)

    private val habitMemoAdapter by lazy {
        HabitMemoAdapter { showMemoBottomSheet(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBindingData()
    }

    private fun initBindingData() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = sharedViewModel
        binding.memoAdapter = habitMemoAdapter
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
            rvMemo.startAnimation(animationSet)
        }
    }

    private fun showMemoBottomSheet(habitMemo: HabitMemoItem) {
        MemoBottomSheet.newInstance(
            habitMemo.memo,
            { memo -> sharedViewModel.saveHabitMemo(habitMemo, memo) },
            { sharedViewModel.deleteHabitMemo(habitMemo) }
        ).show(
            parentFragmentManager,
            Constants.MEMO_TAG
        )
    }
}
