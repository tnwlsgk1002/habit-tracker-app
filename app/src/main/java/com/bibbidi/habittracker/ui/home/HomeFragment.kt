package com.bibbidi.habittracker.ui.home

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
import androidx.viewpager2.widget.ViewPager2
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.FragmentHomeBinding
import com.bibbidi.habittracker.ui.MainEvent
import com.bibbidi.habittracker.ui.MainViewModel
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
import org.threeten.bp.LocalDate

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()
    private val activityViewModel: MainViewModel by activityViewModels()

    private val binding by viewBinding(FragmentHomeBinding::bind)

    private val datePicker: DatePickerBottomSheet
        get() = DatePickerBottomSheet.newInstance(
            viewModel.dateFlow.value,
            onPositiveListener = { viewModel.onSelectDate(it) }
        )

    private val launchAddHabitActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    val data: Intent? = result.data
                    data?.extras?.getParcelable<HabitUiModel>(HABIT_INFO_KEY)?.let {
                        activityViewModel.addHabit(it)
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
                        activityViewModel.updateHabit(it)
                    }
                }

                else -> {}
            }
        }

    private val rowCalendarViewAdapter by lazy {
        RowCalendarAdapter(
            onClick = { dateItem -> viewModel.selectDate(dateItem) },
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
            onClick = { v, habitWithLog -> goToDetailHabit(v, habitWithLog) },
            onCheck = { log, b -> viewModel.checkHabitLog(log, b) },
            onClickMenu = { habitLog, v -> showMenuInHabitLog(habitLog, v) }
        )
    }

    private val rowCalendarViewPagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            if (state == ViewPager2.SCROLL_STATE_IDLE) {
                viewModel.swipeDatePage(
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
                    viewModel.clickDateIcon()
                    true
                }

                else -> false
            }
        }
    }

    private fun initBindingData() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel
        binding.calendarAdapter = rowCalendarViewAdapter
        binding.habitsAdapter = habitsAdapter
        binding.vpRowCalendar.registerOnPageChangeCallback(rowCalendarViewPagerCallback)
    }

    private fun collectEvent() {
        repeatOnStarted {
            viewModel.event.collectLatest { event ->
                when (event) {
                    is HomeEvent.ShowDatePicker -> showDatePicker(event.date)
                    is HomeEvent.ShowAddHabit -> goToAddHabit(event.habit)
                    is HomeEvent.ShowDeleteHabit -> showDeleteWarningDialog(event.habit)
                    is HomeEvent.ShowUpdateHabit -> goToUpdateHabit(event.habit)
                    is HomeEvent.ShowMemoEdit -> showMemoBottomSheet(event.habitLog)
                }
            }
        }

        repeatOnStarted {
            activityViewModel.event.collect { event ->
                when (event) {
                    is MainEvent.SuccessAddHabit -> {
                        showSnackBar(R.string.add_habit_success_message)
                    }

                    is MainEvent.SuccessUpdateHabit -> {
                        showSnackBar(R.string.update_habit_success_message)
                    }

                    is MainEvent.SuccessDeleteHabit -> {
                        showSnackBar(R.string.delete_habit_success_message)
                    }
                }
            }
        }
    }

    private fun showMenuInHabitLog(habitWithLog: HabitWithLogUiModel, view: View) {
        showMenu(view, R.menu.home_habit_menu) { menuItem ->
            when (menuItem.itemId) {
                R.id.option_edit -> viewModel.showUpdateHabit(habitWithLog)
                R.id.option_delete -> viewModel.showDeleteHabit(habitWithLog)
                R.id.option_add_memo -> viewModel.showMemoEdit(habitWithLog)
            }
            true
        }
    }

    private fun showDatePicker(date: LocalDate) {
        datePicker.show(parentFragmentManager, DATE_PICKER_TAG)
    }

    private fun showDeleteWarningDialog(habit: HabitUiModel) {
        deleteHabitDialog.setPositiveButton(getString(R.string.accept)) { _, _ ->
            activityViewModel.deleteHabit(habit)
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

    private fun goToDetailHabit(view: View, habitWithLog: HabitWithLogUiModel) {
        val id = habitWithLog.habit.id ?: error("id is null")
        val action = HomeFragmentDirections.actionHomeFragmentToDetailHabitFragment(id)
        findNavController().navigate(action)
    }

    private fun showMemoBottomSheet(habitWithLog: HabitWithLogUiModel) {
        MemoBottomSheet.newInstance(
            habitWithLog.habitLog.memo,
            { memo -> viewModel.saveHabitMemo(habitWithLog, memo) },
            { viewModel.deleteHabitMemo(habitWithLog) }
        ).show(parentFragmentManager, MEMO_TAG)
    }

    override fun onDestroyView() {
        binding.vpRowCalendar.unregisterOnPageChangeCallback(rowCalendarViewPagerCallback)
        super.onDestroyView()
    }
}
