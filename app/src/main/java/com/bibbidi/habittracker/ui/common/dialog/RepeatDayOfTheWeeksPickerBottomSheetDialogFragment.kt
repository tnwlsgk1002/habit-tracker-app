package com.bibbidi.habittracker.ui.common.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.bibbidi.habittracker.databinding.BottomSheetInputRepeatDayOfTheWeeksBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.threeten.bp.DayOfWeek

class RepeatDayOfTheWeeksPickerBottomSheetDialogFragment : BottomSheetDialogFragment() {

    companion object {
        private const val DAY_OF_THE_WEEKS_KEY = "DAY_OF_THE_WEEKS"

        fun newInstance(
            dayOfTheWeeks: Set<DayOfWeek>,
            onCancelListener: (Set<DayOfWeek>) -> Unit
        ): RepeatDayOfTheWeeksPickerBottomSheetDialogFragment {
            val args = Bundle().apply {
                putStringArray(
                    DAY_OF_THE_WEEKS_KEY,
                    dayOfTheWeeks.map { it.name }.toTypedArray()
                )
            }

            return RepeatDayOfTheWeeksPickerBottomSheetDialogFragment().apply {
                arguments = args
                setOnCancelListener(onCancelListener)
            }
        }
    }

    private var _binding: BottomSheetInputRepeatDayOfTheWeeksBinding? = null

    private val binding get() = _binding!!

    private var dayOfTheWeeks: Set<DayOfWeek> = DayOfWeek.values().toSet()

    private var onCancelListener: ((Set<DayOfWeek>) -> Unit)? = null

    private val dayOfTheWeekToView = mutableMapOf<DayOfWeek, CheckBox>()
    private val viewToDayOfTheWeek = mutableMapOf<CheckBox, DayOfWeek>()

    fun setOnCancelListener(listener: ((Set<DayOfWeek>) -> Unit)?) {
        this.onCancelListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dayOfTheWeeks =
            arguments?.getStringArray(DAY_OF_THE_WEEKS_KEY)?.map { enumValueOf<DayOfWeek>(it) }
                ?.toSet()
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
        dayOfTheWeekToView[DayOfWeek.SUNDAY] = binding.checkboxSun
        dayOfTheWeekToView[DayOfWeek.MONDAY] = binding.checkboxMon
        dayOfTheWeekToView[DayOfWeek.TUESDAY] = binding.checkboxTue
        dayOfTheWeekToView[DayOfWeek.WEDNESDAY] = binding.checkboxWed
        dayOfTheWeekToView[DayOfWeek.THURSDAY] = binding.checkboxThu
        dayOfTheWeekToView[DayOfWeek.FRIDAY] = binding.checkboxFri
        dayOfTheWeekToView[DayOfWeek.SATURDAY] = binding.checkboxSat

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
