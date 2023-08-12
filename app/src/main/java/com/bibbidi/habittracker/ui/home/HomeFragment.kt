package com.bibbidi.habittracker.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.FragmentHomeBinding
import com.bibbidi.habittracker.ui.EditHabitEvent
import com.bibbidi.habittracker.ui.EditHabitLogViewModel
import com.bibbidi.habittracker.ui.EditHabitViewModel
import com.bibbidi.habittracker.ui.common.delegate.viewBinding
import com.bibbidi.habittracker.ui.common.dialog.DatePickerBottomSheet
import com.bibbidi.habittracker.ui.common.dialog.memo.MemoBottomSheet
import com.bibbidi.habittracker.ui.model.habit.HabitUiModel
import com.bibbidi.habittracker.ui.model.habit.HabitWithLogUiModel
import com.bibbidi.habittracker.ui.sethabit.addhabit.AddHabitActivity
import com.bibbidi.habittracker.ui.sethabit.updatehabit.UpdateHabitActivity
import com.bibbidi.habittracker.utils.Constants.DATE_PICKER_TAG
import com.bibbidi.habittracker.utils.Constants.HABIT_INFO_KEY
import com.bibbidi.habittracker.utils.Constants.MEMO_TAG
import com.bibbidi.habittracker.utils.Constants.ROW_CALENDAR_CENTER_POS
import com.bibbidi.habittracker.utils.Constants.ROW_CALENDAR_NEXT_POS
import com.bibbidi.habittracker.utils.Constants.ROW_CALENDAR_PREV_POS
import com.bibbidi.habittracker.utils.repeatOnStarted
import com.bibbidi.habittracker.utils.showMenu
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModels()
    private val editHabitViewModel: EditHabitViewModel by viewModels()
    private val editHabitLogViewModel: EditHabitLogViewModel by viewModels()

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val datePicker: DatePickerBottomSheet
        get() = DatePickerBottomSheet.newInstance(
            homeViewModel.dateFlow.value,
            onPositiveListener = { homeViewModel.onSelectDate(it) }
        )

    private val launchAddHabitActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    val data: Intent? = result.data
                    data?.extras?.getParcelable<HabitUiModel>(HABIT_INFO_KEY)?.let {
                        editHabitViewModel.addHabit(it)
                    }
                }

                else -> {}
            }
        }

    private val launchUpdateHabitActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    val data: Intent? = result.data
                    data?.extras?.getParcelable<HabitUiModel>(HABIT_INFO_KEY)?.let {
                        editHabitViewModel.updateHabit(it)
                    }
                }

                else -> {}
            }
        }

    private val rowCalendarViewAdapter by lazy {
        RowCalendarAdapter(
            onClick = { dateItem -> homeViewModel.selectDate(dateItem) },
            itemRangeInserted = {
                binding.vpRowCalendar.post {
                    binding.vpRowCalendar.setCurrentItem(ROW_CALENDAR_CENTER_POS, false)
                    binding.vpRowCalendar.isUserInputEnabled = true
                }
            }
        )
    }

    private val habitsAdapter by lazy {
        HabitsAdapter(
            onClick = { habitWithLog -> goToDetailHabit(habitWithLog) },
            onCheck = { log, b -> editHabitLogViewModel.checkHabitLog(log, b) },
            onClickMenu = { habitLog, v -> showMenuInHabitLog(habitLog, v) }
        )
    }

    private val rowCalendarViewPagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            if (state == ViewPager2.SCROLL_STATE_IDLE) {
                homeViewModel.swipeDatePage(
                    when (binding.vpRowCalendar.currentItem) {
                        ROW_CALENDAR_PREV_POS -> PageAction.PREV
                        ROW_CALENDAR_NEXT_POS -> PageAction.NEXT
                        else -> return
                    }
                )
                binding.vpRowCalendar.isUserInputEnabled = false
            }
        }
    }

    private val deleteHabitDialog by lazy {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.habit_delete_title)
            .setMessage(R.string.habit_delete_message)
            .setNeutralButton(R.string.cancel, null)
            .setIcon(R.drawable.ic_filled_delete)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBindingData()
        initToolBar()
        collectEvent()
    }

    private fun initToolBar() {
        binding.toolbar.setOnMenuItemClickListener { menu ->
            when (menu.itemId) {
                R.id.menu_select_date -> {
                    homeViewModel.clickDateIcon()
                    true
                }

                else -> false
            }
        }
    }

    private fun initBindingData() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = homeViewModel
        binding.calendarAdapter = rowCalendarViewAdapter
        binding.habitsAdapter = habitsAdapter
        binding.vpRowCalendar.registerOnPageChangeCallback(rowCalendarViewPagerCallback)
    }

    private fun collectEvent() {
        repeatOnStarted {
            homeViewModel.event.collectLatest { event ->
                when (event) {
                    HomeEvent.ShowDatePicker -> showDatePicker()
                    is HomeEvent.ShowAddHabit -> goToAddHabit(event.habit)
                    is HomeEvent.ShowDeleteHabit -> showDeleteWarningDialog(event.habit)
                    is HomeEvent.ShowUpdateHabit -> goToUpdateHabit(event.habit)
                    is HomeEvent.ShowMemoEdit -> showMemoBottomSheet(event.habitLog)
                }
            }
        }

        repeatOnStarted {
            editHabitViewModel.event.collect { event ->
                when (event) {
                    is EditHabitEvent.SuccessAddHabit -> {
                        showSnackBar(R.string.add_habit_success_message)
                    }

                    is EditHabitEvent.SuccessUpdateHabit -> {
                        showSnackBar(R.string.update_habit_success_message)
                    }

                    is EditHabitEvent.SuccessDeleteHabit -> {
                        showSnackBar(R.string.delete_habit_success_message)
                    }
                }
            }
        }
    }

    private fun showMenuInHabitLog(habitWithLog: HabitWithLogUiModel, view: View) {
        showMenu(view, R.menu.home_habit_menu) { menuItem ->
            when (menuItem.itemId) {
                R.id.option_edit -> homeViewModel.showUpdateHabit(habitWithLog)
                R.id.option_delete -> homeViewModel.showDeleteHabit(habitWithLog)
                R.id.option_add_memo -> homeViewModel.showMemoEdit(habitWithLog)
            }
            true
        }
    }

    private fun showDatePicker() {
        datePicker.show(parentFragmentManager, DATE_PICKER_TAG)
    }

    private fun showDeleteWarningDialog(habit: HabitUiModel) {
        deleteHabitDialog.setPositiveButton(getString(R.string.accept)) { _, _ ->
            editHabitViewModel.deleteHabit(habit)
        }.show()
    }

    private fun showSnackBar(@StringRes resId: Int) {
        Snackbar.make(binding.layoutCoordinator, getString(resId), Snackbar.LENGTH_SHORT).show()
    }

    private fun goToUpdateHabit(habitInfo: HabitUiModel) {
        val intent = Intent(activity, UpdateHabitActivity::class.java)
        val bundle = Bundle().apply {
            putParcelable(HABIT_INFO_KEY, habitInfo)
        }
        intent.putExtras(bundle)
        launchUpdateHabitActivity.launch(intent)
    }

    private fun goToAddHabit(habitInfo: HabitUiModel) {
        val intent = Intent(activity, AddHabitActivity::class.java)
        val bundle = Bundle().apply {
            putParcelable(HABIT_INFO_KEY, habitInfo)
        }
        intent.putExtras(bundle)
        launchAddHabitActivity.launch(intent)
    }

    private fun goToDetailHabit(habitWithLog: HabitWithLogUiModel) {
        val id = habitWithLog.habit.id ?: error("id is null")
        val action = HomeFragmentDirections.actionHomeFragmentToDetailHabitFragment(id)
        findNavController().navigate(action)
    }

    private fun showMemoBottomSheet(habitWithLog: HabitWithLogUiModel) {
        MemoBottomSheet.newInstance(
            habitWithLog.habitLog.memo,
            { memo -> editHabitLogViewModel.saveHabitMemo(habitWithLog, memo) },
            { editHabitLogViewModel.deleteHabitMemo(habitWithLog) }
        ).show(parentFragmentManager, MEMO_TAG)
    }

    override fun onDestroyView() {
        binding.vpRowCalendar.unregisterOnPageChangeCallback(rowCalendarViewPagerCallback)
        super.onDestroyView()
    }
}
