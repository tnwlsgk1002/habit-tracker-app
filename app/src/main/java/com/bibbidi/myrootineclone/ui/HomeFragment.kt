package com.bibbidi.myrootineclone.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bibbidi.myrootineclone.R
import com.bibbidi.myrootineclone.databinding.FragmentHomeBinding
import com.bibbidi.myrootineclone.ui.customview.DateState
import com.bibbidi.myrootineclone.ui.customview.DayOfTheWeek

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

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

        // TODO : 아이템 클릭 시 발생시킬 이벤트 추가
        val dateViewAdapter = RowCalendarAdapter {
        }.apply {
            submitList(sampleList)
        }

        binding.vpRowCalendar.adapter = dateViewAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_select_date -> {
                // TODO("날짜 선택")
                true
            }
            R.id.menu_set_up -> {
                // TODO("설정")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
