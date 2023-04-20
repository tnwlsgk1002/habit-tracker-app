package com.bibbidi.myrootineclone.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bibbidi.myrootineclone.R
import com.bibbidi.myrootineclone.databinding.FragmentHomeBinding
import com.bibbidi.myrootineclone.ui.customview.DateState
import com.bibbidi.myrootineclone.ui.customview.DayOfTheWeek
import com.google.android.material.datepicker.MaterialDatePicker

class HomeFragment : Fragment() {

    companion object {
        const val datePickerTag = "datePicker"
    }

    private lateinit var viewModel: HomeViewModel

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
        val sampleList = mutableListOf(
            arrayOf(
                DateItem(false, DateState.RED, 1, DayOfTheWeek.SUN, true),
                DateItem(false, DateState.RED, 2, DayOfTheWeek.MON, true),
                DateItem(false, DateState.RED, 3, DayOfTheWeek.TUE, true),
                DateItem(false, DateState.RED, 4, DayOfTheWeek.WED, true),
                DateItem(false, DateState.GRAY, 5, DayOfTheWeek.THU, true),
                DateItem(true, DateState.YELLOW, 6, DayOfTheWeek.FRI, true),
                DateItem(false, DateState.GREEN, 7, DayOfTheWeek.SAT, true)
            ),
            arrayOf(
                DateItem(true, DateState.GRAY, 8, DayOfTheWeek.SUN, true),
                DateItem(false, DateState.YELLOW, 9, DayOfTheWeek.MON, true),
                DateItem(false, DateState.RED, 10, DayOfTheWeek.TUE, true),
                DateItem(false, DateState.GRAY, 11, DayOfTheWeek.WED, true),
                DateItem(false, DateState.GRAY, 12, DayOfTheWeek.THU, true),
                DateItem(false, DateState.YELLOW, 13, DayOfTheWeek.FRI, true),
                DateItem(false, DateState.YELLOW, 14, DayOfTheWeek.SAT, true)
            ),
            arrayOf(
                DateItem(true, DateState.RED, 15, DayOfTheWeek.SUN, true),
                DateItem(false, DateState.RED, 16, DayOfTheWeek.MON, true),
                DateItem(false, DateState.RED, 17, DayOfTheWeek.TUE, true),
                DateItem(false, DateState.RED, 18, DayOfTheWeek.WED, true),
                DateItem(false, DateState.GRAY, 19, DayOfTheWeek.THU, true),
                DateItem(false, DateState.YELLOW, 20, DayOfTheWeek.FRI, true),
                DateItem(false, DateState.GREEN, 21, DayOfTheWeek.SAT, true)
            ),
            arrayOf(
                DateItem(true, DateState.RED, 22, DayOfTheWeek.SUN, true),
                DateItem(false, DateState.RED, 23, DayOfTheWeek.MON, true),
                DateItem(false, DateState.GRAY, 24, DayOfTheWeek.TUE, true),
                DateItem(false, DateState.GRAY, 25, DayOfTheWeek.WED, true),
                DateItem(false, DateState.GRAY, 26, DayOfTheWeek.THU, true),
                DateItem(false, DateState.GREEN, 27, DayOfTheWeek.FRI, true),
                DateItem(false, DateState.RED, 28, DayOfTheWeek.SAT, true)
            )
        )

        with(binding.vpRowCalendar) {
            // TODO : 아이템 클릭 시 발생시킬 이벤트 추가
            val dateViewAdapter = RowCalendarAdapter {
            }.apply {
                submitList(sampleList)
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
            R.id.menu_set_up -> {
                // TODO("설정")
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
