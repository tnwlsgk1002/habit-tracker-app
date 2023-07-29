package com.bibbidi.habittracker.ui.detailhabit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.FragmentDetailHabitBinding
import com.bibbidi.habittracker.ui.MainEvent
import com.bibbidi.habittracker.ui.MainViewModel
import com.bibbidi.habittracker.ui.common.Constants
import com.bibbidi.habittracker.ui.common.Constants.HABIT_INFO_KEY
import com.bibbidi.habittracker.ui.common.delegate.viewBinding
import com.bibbidi.habittracker.ui.model.habit.HabitUiModel
import com.bibbidi.habittracker.ui.sethabit.updatehabit.UpdateHabitActivity
import com.bibbidi.habittracker.utils.repeatOnStarted
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class DetailHabitFragment : Fragment(R.layout.fragment_detail_habit) {

    private val viewModel: DetailViewModel by viewModels()
    private val activityViewModel: MainViewModel by activityViewModels()

    private val binding by viewBinding(FragmentDetailHabitBinding::bind)

    private val habitMemoAdapter by lazy {
        HabitMemoAdapter()
    }

    private val launchUpdateHabitActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    val data: Intent? = result.data
                    data?.extras?.getParcelable<HabitUiModel>(HABIT_INFO_KEY)?.let {
                        activityViewModel.updateHabit(it)
                    }
                }
                else -> {}
            }
        }

    private val deleteHabitDialog by lazy {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.habit_delete_title)
            .setMessage(R.string.habit_delete_message)
            .setNeutralButton(R.string.cancel, null)
            .setIcon(R.drawable.ic_filled_delete)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initBindingData()
        collectEvent()
    }

    private fun initToolbar() {
        binding.toolbar.run {
            setOnMenuItemClickListener { menu ->
                when (menu.itemId) {
                    R.id.option_edit -> {
                        viewModel.showUpdateHabit()
                        true
                    }
                    R.id.option_delete -> {
                        viewModel.showDeleteHabit()
                        true
                    }
                    else -> false
                }
            }

            setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun initBindingData() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        binding.memoAdapter = habitMemoAdapter
    }

    private fun collectEvent() {
        repeatOnStarted {
            viewModel.event.collectLatest { event ->
                when (event) {
                    is DetailHabitEvent.ShowUpdateHabit -> goToUpdateHabit(event.habit)
                    is DetailHabitEvent.ShowDeleteHabit -> showDeleteWarningDialog(event.habit)
                }
            }
        }

        repeatOnStarted {
            activityViewModel.event.collectLatest { event ->
                when (event) {
                    is MainEvent.SuccessUpdateHabit -> {
                        viewModel.fetchHabit()
                        showSnackBar(R.string.update_habit_success_message)
                    }
                    is MainEvent.SuccessDeleteHabit -> {
                        goToHomeFragmentAfterDelete()
                    }
                    else -> {}
                }
            }
        }
    }

    private fun goToHomeFragmentAfterDelete() {
        val action = DetailHabitFragmentDirections.actionDetailHabitFragmentToHomeFragment()
        findNavController().navigate(action)
    }

    private fun showSnackBar(@StringRes resId: Int) {
        Snackbar.make(binding.root, getString(resId), Snackbar.LENGTH_SHORT).show()
    }

    private fun goToUpdateHabit(habitInfo: HabitUiModel) {
        val intent = Intent(activity, UpdateHabitActivity::class.java)
        val bundle = Bundle().apply {
            putParcelable(Constants.HABIT_INFO_KEY, habitInfo)
        }
        intent.putExtras(bundle)
        launchUpdateHabitActivity.launch(intent)
    }

    private fun showDeleteWarningDialog(habit: HabitUiModel) {
        deleteHabitDialog.setPositiveButton(getString(R.string.accept)) { _, _ ->
            activityViewModel.deleteHabit(habit)
        }.show()
    }
}
