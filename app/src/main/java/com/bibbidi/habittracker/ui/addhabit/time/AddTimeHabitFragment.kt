package com.bibbidi.habittracker.ui.addhabit.time

import androidx.fragment.app.viewModels
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.FragmentAddTimeHabitBinding
import com.bibbidi.habittracker.ui.addhabit.AddHabitFragment
import com.bibbidi.habittracker.ui.common.Constants.GOAL_TIME_PICKER_TAG
import com.bibbidi.habittracker.ui.common.delegate.viewBinding
import com.bibbidi.habittracker.ui.common.dialog.GoalTimePickerBottomSheet
import com.bibbidi.habittracker.utils.repeatOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import org.threeten.bp.Duration

@AndroidEntryPoint
class AddTimeHabitFragment : AddHabitFragment(R.layout.fragment_add_time_habit) {

    override val viewModel: AddTimeHabitViewModel by viewModels()

    override val binding by viewBinding(FragmentAddTimeHabitBinding::bind)

    private val goalTimePickerBottomSheetDialogFragment: GoalTimePickerBottomSheet by lazy {
        GoalTimePickerBottomSheet.newInstance(
            viewModel.goalTimeFlow.value.toHoursPart(),
            viewModel.goalTimeFlow.value.toMillisPart()
        ) { hour, minute ->
            viewModel.setGoalTime(Duration.ofHours(hour.toLong()).plusMinutes(minute.toLong()))
        }
    }

    override fun initBindingData() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
    }

    override fun collectEvent() {
        super.collectEvent()
        repeatOnStarted {
            viewModel.goalTimeClickEvent.collectLatest {
                goalTimePickerBottomSheetDialogFragment.show(
                    parentFragmentManager,
                    GOAL_TIME_PICKER_TAG
                )
            }
        }
    }
}
