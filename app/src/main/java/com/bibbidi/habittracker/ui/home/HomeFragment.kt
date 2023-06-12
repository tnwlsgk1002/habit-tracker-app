package com.bibbidi.habittracker.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.FragmentHomeBinding
import com.bibbidi.habittracker.ui.addhabit.AddHabitActivity
import com.bibbidi.habittracker.ui.addhabit.AddHabitActivity.Companion.HABIT_INFO_KEY
import com.bibbidi.habittracker.ui.addhabit.AddHabitActivity.Companion.HABIT_TYPE_KEY
import com.bibbidi.habittracker.ui.common.viewBindings
import com.bibbidi.habittracker.ui.model.habit.HabitTypeUiModel
import com.bibbidi.habittracker.ui.model.habit.habitinfo.HabitInfoUiModel
import com.bibbidi.habittracker.ui.model.habit.log.HabitLogUiModel
import com.bibbidi.habittracker.utils.asLocalDate
import com.bibbidi.habittracker.utils.asLong
import com.bibbidi.habittracker.utils.repeatOnStarted
import com.bibbidi.habittracker.utils.showMenu
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import org.threeten.bp.LocalDate

@AndroidEntryPoint
class HomeFragment :
    Fragment(R.layout.fragment_home),
    SelectHabitTypeBottomSheetDialogFragment.OnHabitTypeButtonClickListener {

    companion object {
        const val DATE_PICKER_TAG = "datePicker"
        const val PREV_POS = 0
        const val NEXT_POS = 2
        const val CENTER_POS = 1
    }

    private val viewModel: HomeViewModel by viewModels()

    private val binding by viewBindings(FragmentHomeBinding::bind)

    private fun getDatePicker(date: LocalDate) = MaterialDatePicker.Builder.datePicker()
        .setTitleText(R.string.select_date)
        .setSelection(date.asLong())
        .build().apply {
            addOnPositiveButtonClickListener {
                viewModel.pickDate(it.asLocalDate())
            }
        }

    private val addHabitBottomSheet: SelectHabitTypeBottomSheetDialogFragment by lazy {
        SelectHabitTypeBottomSheetDialogFragment().apply {
            setOnHabitTypeButtonClickListener(this@HomeFragment)
        }
    }

    private val launchSetHabitActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            when (result.resultCode) {
                Activity.RESULT_OK -> {
                    val data: Intent? = result.data
                    data?.extras?.getParcelable<HabitInfoUiModel>(HABIT_INFO_KEY)?.let {
                        viewModel.setHabit(it)
                    }
                }
                else -> {}
            }
        }

    private val rowCalendarViewAdapter by lazy {
        RowCalendarAdapter(
            onClick = { dateItem -> viewModel.clickDateItem(dateItem) },
            itemRangeInserted = {
                binding.vpRowCalendar.post { binding.vpRowCalendar.setCurrentItem(CENTER_POS, false) }
            }
        )
    }

    private val habitsAdapter by lazy {
        HabitsAdapter(
            onCheckBox = { _, _ -> },
            onTurnStopWatch = { _, _ -> },
            onClickRecordButton = { _ -> },
            onClickMenu = { habitLog, v -> showMenuInHabitLog(habitLog, v) }
        )
    }

    private val rowCalendarViewPagerCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageScrollStateChanged(state: Int) {
            super.onPageScrollStateChanged(state)
            if (state == ViewPager2.SCROLL_STATE_IDLE) {
                when (binding.vpRowCalendar.currentItem) {
                    PREV_POS -> viewModel.swipeDatePages(PageAction.PREV)
                    NEXT_POS -> viewModel.swipeDatePages(PageAction.NEXT)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBindingData()
        collectEvent()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_select_date -> {
                viewModel.clickDateIcon()
                true
            }
            else -> super.onOptionsItemSelected(item)
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
                    HomeEvent.ShowSelectHabitType -> showSelectHabitTypeBottomSheet()
                    HomeEvent.ShowTrackValueDialog -> {}
                    HomeEvent.SuccessAddHabit -> showSnackBar(R.string.set_habit_success_message)
                }
            }
        }
    }

    private fun showMenuInHabitLog(habitLog: HabitLogUiModel, view: View) {
        showMenu(view, R.menu.habit_menu) { menuItem ->
            when (menuItem.itemId) {
                R.id.option_edit -> Toast.makeText(context, "수정", Toast.LENGTH_SHORT).show()
                R.id.option_delete -> Toast.makeText(context, "삭제", Toast.LENGTH_SHORT)
                    .show()
            }
            true
        }
    }

    private fun showDatePicker(date: LocalDate) {
        getDatePicker(date).show(parentFragmentManager, DATE_PICKER_TAG)
    }

    private fun showSelectHabitTypeBottomSheet() {
        addHabitBottomSheet.show(parentFragmentManager, addHabitBottomSheet.tag)
    }

    private fun showSnackBar(@StringRes resId: Int) {
        Snackbar.make(binding.layoutCoordinator, getString(resId), Snackbar.LENGTH_SHORT).show()
    }

    override fun onHabitTypeButtonClick(type: HabitTypeUiModel) {
        val intent = Intent(activity, AddHabitActivity::class.java)
        val bundle = Bundle().apply {
            putParcelable(HABIT_TYPE_KEY, type)
        }
        intent.putExtras(bundle)
        launchSetHabitActivity.launch(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.vpRowCalendar.unregisterOnPageChangeCallback(rowCalendarViewPagerCallback)
    }
}
