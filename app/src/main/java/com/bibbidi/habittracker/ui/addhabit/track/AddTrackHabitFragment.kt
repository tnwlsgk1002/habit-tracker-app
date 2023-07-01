package com.bibbidi.habittracker.ui.addhabit.track

import androidx.fragment.app.viewModels
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.FragmentAddTrackHabitBinding
import com.bibbidi.habittracker.ui.addhabit.AddHabitFragment
import com.bibbidi.habittracker.ui.common.delegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddTrackHabitFragment : AddHabitFragment(R.layout.fragment_add_track_habit) {

    override val viewModel: AddTrackHabitViewModel by viewModels()

    override val binding by viewBinding(FragmentAddTrackHabitBinding::bind)

    override fun initBindingData() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
    }
}
