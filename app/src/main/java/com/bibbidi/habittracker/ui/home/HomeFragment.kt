package com.bibbidi.habittracker.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.FragmentHomeBinding
import com.bibbidi.habittracker.ui.ItemDecoration
import com.bibbidi.habittracker.ui.customview.DayOfTheWeek
import com.bibbidi.habittracker.ui.home.dates.DateItem
import com.bibbidi.habittracker.ui.home.dates.RowCalendarAdapter
import com.bibbidi.habittracker.ui.home.habits.CheckHabitItem
import com.bibbidi.habittracker.ui.home.habits.HabitsAdapter
import com.bibbidi.habittracker.ui.home.habits.TimeHabitItem
import com.bibbidi.habittracker.ui.home.habits.TrackHabitItem
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import org.threeten.bp.LocalTime

@AndroidEntryPoint
class HomeFragment : Fragment() {

    companion object {
        const val datePickerTag = "datePicker"

        const val HabitItemPadding = 10
    }

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val datePicker = MaterialDatePicker.Builder.datePicker()
        .setTitleText(R.string.select_date)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAdapter()
        setUpListener()
    }

    private fun setUpAdapter() {
        // TODO : dummy data
        val sampleDateViews = mutableListOf(
            arrayOf(
                DateItem(false, 1, DayOfTheWeek.SUN, true),
                DateItem(false, 2, DayOfTheWeek.MON, true),
                DateItem(false, 3, DayOfTheWeek.TUE, true),
                DateItem(false, 4, DayOfTheWeek.WED, true),
                DateItem(false, 5, DayOfTheWeek.THU, true),
                DateItem(true, 6, DayOfTheWeek.FRI, true),
                DateItem(false, 7, DayOfTheWeek.SAT, true)
            ),
            arrayOf(
                DateItem(true, 8, DayOfTheWeek.SUN, true),
                DateItem(false, 9, DayOfTheWeek.MON, true),
                DateItem(false, 10, DayOfTheWeek.TUE, true),
                DateItem(false, 11, DayOfTheWeek.WED, true),
                DateItem(false, 12, DayOfTheWeek.THU, true),
                DateItem(true, 13, DayOfTheWeek.FRI, true),
                DateItem(false, 14, DayOfTheWeek.SAT, true)
            ),
            arrayOf(
                DateItem(true, 15, DayOfTheWeek.SUN, true),
                DateItem(false, 16, DayOfTheWeek.MON, true),
                DateItem(false, 17, DayOfTheWeek.TUE, true),
                DateItem(false, 18, DayOfTheWeek.WED, true),
                DateItem(true, 19, DayOfTheWeek.THU, true),
                DateItem(false, 20, DayOfTheWeek.FRI, true),
                DateItem(false, 21, DayOfTheWeek.SAT, true)
            ),
            arrayOf(
                DateItem(true, 22, DayOfTheWeek.SUN, true),
                DateItem(false, 23, DayOfTheWeek.MON, true),
                DateItem(false, 24, DayOfTheWeek.TUE, true),
                DateItem(false, 25, DayOfTheWeek.WED, true),
                DateItem(false, 26, DayOfTheWeek.THU, true),
                DateItem(false, 27, DayOfTheWeek.FRI, true),
                DateItem(false, 28, DayOfTheWeek.SAT, true)
            )
        )

        val sampleHabits = listOf(
            CheckHabitItem(5L, 0, "💧", "체크", true, "저녁에", true),
            CheckHabitItem(6L, 0, "✏", "체크", true, "저녁에", false),
            TimeHabitItem(
                1L,
                0,
                "💄",
                "타이머1 - 기록있음",
                true,
                "아침에",
                LocalTime.of(4, 0),
                LocalTime.of(1, 0),
                isStarted = false
            ),
            TimeHabitItem(
                2L,
                0,
                "\uD83D\uDC84",
                "타이머2 - 기록없음",
                false,
                "아침에",
                LocalTime.of(4, 0),
                LocalTime.of(0, 0),
                isStarted = true
            ),
            TrackHabitItem(3L, 0, "💛", "추적1", true, "낮에", null),
            TrackHabitItem(4L, 0, "✅", "추적2", false, "낮에", 84)
        )

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
