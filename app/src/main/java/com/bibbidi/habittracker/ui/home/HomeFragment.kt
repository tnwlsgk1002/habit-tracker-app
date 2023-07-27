package com.bibbidi.habittracker.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.FragmentHomeBinding
import com.bibbidi.habittracker.ui.background.AlarmHelper
import com.bibbidi.habittracker.ui.common.Constants.DATE_PICKER_TAG
import com.bibbidi.habittracker.ui.common.Constants.HABIT_INFO_KEY
import com.bibbidi.habittracker.ui.common.Constants.ROW_CALENDAR_CENTER_POS
import com.bibbidi.habittracker.ui.common.Constants.ROW_CALENDAR_NEXT_POS
import com.bibbidi.habittracker.ui.common.Constants.ROW_CALENDAR_PREV_POS
import com.bibbidi.habittracker.ui.common.delegate.viewBinding
import com.bibbidi.habittracker.ui.model.habit.HabitLogUiModel
import com.bibbidi.habittracker.ui.model.habit.HabitUiModel
import com.bibbidi.habittracker.ui.sethabit.addhabit.AddHabitActivity
import com.bibbidi.habittracker.ui.sethabit.updatehabit.UpdateHabitActivity
import com.bibbidi.habittracker.utils.asLocalDate
import com.bibbidi.habittracker.utils.asLong
import com.bibbidi.habittracker.utils.repeatOnStarted
import com.bibbidi.habittracker.utils.showMenu
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import org.threeten.bp.LocalDate
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    private val binding by viewBinding(FragmentHomeBinding::bind)

    @Inject
    lateinit var alarmHelper: AlarmHelper

    private fun getDatePicker(date: LocalDate) = MaterialDatePicker.Builder.datePicker()
        .setTitleText(R.string.select_date)
        .setSelection(date.asLong())
        .build().apply {
            addOnPositiveButtonClickListener {
                viewModel.onSelectDate(it.asLocalDate())
            }
        }

    private val launchAddHabitActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    val data: Intent? = result.data
                    data?.extras?.getParcelable<HabitUiModel>(HABIT_INFO_KEY)?.let {
                        viewModel.onSetHabit(it)
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
                        viewModel.updateHabit(it)
                    }
                }
                else -> {}
            }
        }

    private val rowCalendarViewAdapter by lazy {
        RowCalendarAdapter(
            onClick = { dateItem -> viewModel.onSelectDate(dateItem) },
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
            onCheck = { log, b -> viewModel.updateHabitLog(log, b) },
            onClickMenu = { habitLog, v -> showMenuInHabitLog(habitLog, v) }
        )
    }

    private val rowCalendarViewPagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            if (state == ViewPager2.SCROLL_STATE_IDLE) {
                viewModel.onSwipeDatePage(
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
            viewModel.event.collectLatest {
                when (it) {
                    is HomeEvent.ShowDatePicker -> showDatePicker(it.date)
                    is HomeEvent.SuccessAddHabit -> {
                        showSnackBar(R.string.add_habit_success_message)
                        alarmHelper.registerAlarm(it.alarmInfo)
                    }
                    is HomeEvent.SuccessUpdateHabit -> {
                        showSnackBar(R.string.update_habit_success_message)
                        alarmHelper.updateAlarm(it.alarmInfo)
                    }
                    is HomeEvent.SuccessDeleteHabit -> {
                        showSnackBar(R.string.delete_habit_success_message)
                        alarmHelper.cancelAlarm(it.alarmInfo)
                    }
                    is HomeEvent.AttemptAddHabit -> goToAddHabit(it.habitInfo)
                    is HomeEvent.AttemptDeleteHabit -> showDeleteWarningDialog(it.habitLog)
                    is HomeEvent.AttemptUpdateHabit -> goToUpdateHabit(it.habitInfo)
                }
            }
        }
    }

    private fun showMenuInHabitLog(habitLog: HabitLogUiModel, view: View) {
        showMenu(view, R.menu.habit_menu) { menuItem ->
            when (menuItem.itemId) {
                R.id.option_edit -> viewModel.onUpdateHabit(habitLog)
                R.id.option_delete -> viewModel.onDeleteHabit(habitLog)
            }
            true
        }
    }

    private fun showDatePicker(date: LocalDate) {
        getDatePicker(date).show(parentFragmentManager, DATE_PICKER_TAG)
    }

    private fun showDeleteWarningDialog(habitLog: HabitLogUiModel) {
        deleteHabitDialog.setPositiveButton(getString(R.string.accept)) { _, _ ->
            viewModel.deleteHabit(habitLog)
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

    override fun onDestroyView() {
        binding.vpRowCalendar.unregisterOnPageChangeCallback(rowCalendarViewPagerCallback)
        super.onDestroyView()
    }
}
