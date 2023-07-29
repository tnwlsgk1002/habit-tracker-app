package com.bibbidi.habittracker.ui.detailhabit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.FragmentDetailHabitBinding
import com.bibbidi.habittracker.ui.common.delegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailHabitFragment : Fragment(R.layout.fragment_detail_habit) {

    private val viewModel: DetailViewModel by viewModels()

    private val binding by viewBinding(FragmentDetailHabitBinding::bind)

    private val habitMemoAdapter by lazy {
        HabitMemoAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initBindingData()
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initBindingData() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        binding.memoAdapter = habitMemoAdapter
    }
}
