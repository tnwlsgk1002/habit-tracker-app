package com.bibbidi.habittracker.ui.addhabit.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.bibbidi.habittracker.databinding.BottomSheetInputRepeatDayOfTheWeeksBinding
import com.bibbidi.habittracker.ui.model.DayOfTheWeek
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RepeatDayOfTheWeeksPickerBottomSheetDialogFragment : BottomSheetDialogFragment() {

    companion object {
        private const val DAY_OF_THE_WEEKS_KEY = "DAY_OF_THE_WEEKS"

        fun newInstance(
            dayOfTheWeeks: Set<DayOfTheWeek>,
            onCancelListener: (Set<DayOfTheWeek>) -> Unit
        ): RepeatDayOfTheWeeksPickerBottomSheetDialogFragment {
            val args = Bundle().apply {
                putParcelableArrayList(DAY_OF_THE_WEEKS_KEY, ArrayList(dayOfTheWeeks))
            }

            return RepeatDayOfTheWeeksPickerBottomSheetDialogFragment().apply {
                arguments = args
                setOnCancelListener(onCancelListener)
            }
        }
    }

    private var _binding: BottomSheetInputRepeatDayOfTheWeeksBinding? = null

    private val binding get() = _binding!!

    private var dayOfTheWeeks: Set<DayOfTheWeek> = DayOfTheWeek.values().toSet()

    private var onCancelListener: ((Set<DayOfTheWeek>) -> Unit)? = null

    private val dayOfTheWeekToView = mutableMapOf<DayOfTheWeek, CheckBox>()
    private val viewToDayOfTheWeek = mutableMapOf<CheckBox, DayOfTheWeek>()

    fun setOnCancelListener(listener: ((Set<DayOfTheWeek>) -> Unit)?) {
        this.onCancelListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dayOfTheWeeks =
            arguments?.getParcelableArrayList<DayOfTheWeek>(DAY_OF_THE_WEEKS_KEY)?.toSet()
                ?: dayOfTheWeeks
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetInputRepeatDayOfTheWeeksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpMapData()
        setUpViewData()
    }

    private fun setUpMapData() {
        dayOfTheWeekToView[DayOfTheWeek.SUN] = binding.checkboxSun
        dayOfTheWeekToView[DayOfTheWeek.MON] = binding.checkboxMon
        dayOfTheWeekToView[DayOfTheWeek.TUE] = binding.checkboxTue
        dayOfTheWeekToView[DayOfTheWeek.WED] = binding.checkboxWed
        dayOfTheWeekToView[DayOfTheWeek.THU] = binding.checkboxThu
        dayOfTheWeekToView[DayOfTheWeek.FRI] = binding.checkboxFri
        dayOfTheWeekToView[DayOfTheWeek.SAT] = binding.checkboxSat

        dayOfTheWeekToView.forEach { (dayOfTheWeeks, view) ->
            viewToDayOfTheWeek[view] = dayOfTheWeeks
        }
    }

    private fun setUpViewData() {
        dayOfTheWeeks.forEach {
            dayOfTheWeekToView[it]?.isChecked = true
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)

        val checkedDayOfTheWeeks = viewToDayOfTheWeek.filterKeys { it.isChecked }.values.toSet()
        onCancelListener?.invoke(checkedDayOfTheWeeks)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
