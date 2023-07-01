package com.bibbidi.habittracker.ui.addhabit.check

import androidx.fragment.app.viewModels
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.FragmentAddCheckHabitBinding
import com.bibbidi.habittracker.ui.addhabit.AddHabitFragment
import com.bibbidi.habittracker.ui.common.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCheckHabitFragment : AddHabitFragment(R.layout.fragment_add_check_habit) {

    override val viewModel: AddCheckHabitViewModel by viewModels()

    override val binding by viewBinding(FragmentAddCheckHabitBinding::bind)

    override fun initBindingData() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
    }
}
