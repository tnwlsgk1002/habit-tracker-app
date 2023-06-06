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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.FragmentHomeBinding
import com.bibbidi.habittracker.ui.addhabit.AddHabitActivity
import com.bibbidi.habittracker.ui.addhabit.AddHabitActivity.Companion.HABIT_INFO_KEY
import com.bibbidi.habittracker.ui.addhabit.AddHabitActivity.Companion.HABIT_TYPE_KEY
import com.bibbidi.habittracker.ui.common.ItemDecoration
import com.bibbidi.habittracker.ui.common.viewBindings
import com.bibbidi.habittracker.ui.model.date.DateItem
import com.bibbidi.habittracker.ui.model.habit.HabitTypeUiModel
import com.bibbidi.habittracker.ui.model.habit.habitinfo.HabitInfoUiModel
import com.bibbidi.habittracker.ui.model.habit.log.HabitLogUiModel
import com.bibbidi.habittracker.utils.repeatOnStarted
import com.bibbidi.habittracker.utils.showMenu
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment :
    Fragment(R.layout.fragment_home),
    SelectHabitTypeBottomSheetDialogFragment.OnHabitTypeButtonClickListener {

    companion object {
        const val datePickerTag = "datePicker"

        const val HabitItemPadding = 10
    }

    private val viewModel: HomeViewModel by viewModels()

    private val binding by viewBindings(FragmentHomeBinding::bind)

    private val datePicker = MaterialDatePicker.Builder.datePicker()
        .setTitleText(R.string.select_date)
        .build()

    private lateinit var bottomSheetDialogFragment: SelectHabitTypeBottomSheetDialogFragment

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpAdapter()
        setUpListener()
        setUpFab()
        setUpBottomSheet()
        collectEvent()
    }

    private fun setUpAdapter() {
        // TODO : dummy data
        val sampleDateViews = listOf<Array<DateItem>>()
        val sampleHabits = listOf<HabitLogUiModel>()

        with(binding.vpRowCalendar) {
            // TODO : 아이템 클릭 시 발생시킬 이벤트 추가
            val dateViewAdapter = RowCalendarAdapter {}.apply {
                submitList(sampleDateViews)
            }

            adapter = dateViewAdapter

            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)

                    val nextPosition = position + 1
                    if (nextPosition < dateViewAdapter.itemCount) {
                        val recyclerView = getChildAt(0) as RecyclerView
                        val viewHolder = recyclerView.findViewHolderForAdapterPosition(nextPosition)
                        if (viewHolder is RowCalendarAdapter.DateItemViewHolder) {
                            viewHolder.startShimmer()
                        }
                    }
                }

                // TODO : 데이터 로드 후 stopShimmer() 호출
            })
        }

        with(binding.rvNotFinishedHabits) {
            // TODO : 아이템 클릭 시 발생시킬 이벤트 추가
            val finishedHabitAdapter = HabitsAdapter(onCheckBox = { checkHabitItem, b ->
                Toast.makeText(context, "$checkHabitItem : $b", Toast.LENGTH_LONG).show()
            }, onTurnStopWatch = { timeHabitItem, b ->
                    Toast.makeText(context, "$timeHabitItem : $b", Toast.LENGTH_LONG).show()
                }, onClickRecordButton = { trackHabitItem ->
                    Toast.makeText(context, "$trackHabitItem click", Toast.LENGTH_LONG).show()
                }, onClickMenu = { _, view ->
                    showMenu(view, R.menu.habit_menu) { menuItem ->
                        when (menuItem.itemId) {
                            R.id.option_edit -> Toast.makeText(context, "수정", Toast.LENGTH_SHORT).show()
                            R.id.option_delete -> Toast.makeText(context, "삭제", Toast.LENGTH_SHORT)
                                .show()
                        }
                        true
                    }
                }).apply {
                submitList(sampleHabits)
            }

            adapter = finishedHabitAdapter
            addItemDecoration(ItemDecoration(HabitItemPadding))
        }
    }

    private fun setUpListener() {
        // TODO: 날짜 선택 시 변경
        datePicker.addOnPositiveButtonClickListener {
            Toast.makeText(context, "$it", Toast.LENGTH_LONG).show()
        }
    }

    private fun setUpFab() {
        binding.fabMain.setOnClickListener {
            showSelectHabitTypeBottomSheet()
        }
    }

    private fun setUpBottomSheet() {
        bottomSheetDialogFragment = SelectHabitTypeBottomSheetDialogFragment()
        bottomSheetDialogFragment.setOnHabitTypeButtonClickListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_select_date -> {
                showDatePicker()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showDatePicker() {
        // TODO: 선택한 날짜를 현재 선택된 날짜로 변경 (datePicker.selection)
        datePicker.show(parentFragmentManager, datePickerTag)
    }

    private fun showSelectHabitTypeBottomSheet() {
        bottomSheetDialogFragment.show(parentFragmentManager, bottomSheetDialogFragment.tag)
    }

    private fun collectEvent() {
        repeatOnStarted {
            viewModel.messageEvent.collectLatest {
                val message = getString(
                    when (it) {
                        HomeMessageEvent.SuccessAddHabit -> R.string.set_habit_success_message
                    }
                )
                Snackbar.make(binding.layoutCoordinator, message, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onHabitTypeButtonClick(type: HabitTypeUiModel) {
        val intent = Intent(activity, AddHabitActivity::class.java)
        val bundle = Bundle().apply {
            putParcelable(HABIT_TYPE_KEY, type)
        }
        intent.putExtras(bundle)
        launchSetHabitActivity.launch(intent)
    }
}
