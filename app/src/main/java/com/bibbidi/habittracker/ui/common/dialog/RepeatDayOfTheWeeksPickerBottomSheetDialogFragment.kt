package com.bibbidi.habittracker.ui.common.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.bibbidi.habittracker.databinding.BottomSheetInputRepeatDayOfTheWeeksBinding
import com.bibbidi.habittracker.ui.model.DayOfTheWeekUiModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RepeatDayOfTheWeeksPickerBottomSheetDialogFragment : BottomSheetDialogFragment() {

    companion object {
        private const val DAY_OF_THE_WEEKS_KEY = "DAY_OF_THE_WEEKS"

        fun newInstance(
            dayOfTheWeeks: Set<DayOfTheWeekUiModel>,
            onCancelListener: (Set<DayOfTheWeekUiModel>) -> Unit
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

    private var dayOfTheWeeks: Set<DayOfTheWeekUiModel> = DayOfTheWeekUiModel.values().toSet()

    private var onCancelListener: ((Set<DayOfTheWeekUiModel>) -> Unit)? = null

    private val dayOfTheWeekToView = mutableMapOf<DayOfTheWeekUiModel, CheckBox>()
    private val viewToDayOfTheWeek = mutableMapOf<CheckBox, DayOfTheWeekUiModel>()

    fun setOnCancelListener(listener: ((Set<DayOfTheWeekUiModel>) -> Unit)?) {
        this.onCancelListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dayOfTheWeeks =
            arguments?.getParcelableArrayList<DayOfTheWeekUiModel>(DAY_OF_THE_WEEKS_KEY)?.toSet()
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
        dayOfTheWeekToView[DayOfTheWeekUiModel.SUN] = binding.checkboxSun
        dayOfTheWeekToView[DayOfTheWeekUiModel.MON] = binding.checkboxMon
        dayOfTheWeekToView[DayOfTheWeekUiModel.TUE] = binding.checkboxTue
        dayOfTheWeekToView[DayOfTheWeekUiModel.WED] = binding.checkboxWed
        dayOfTheWeekToView[DayOfTheWeekUiModel.THU] = binding.checkboxThu
        dayOfTheWeekToView[DayOfTheWeekUiModel.FRI] = binding.checkboxFri
        dayOfTheWeekToView[DayOfTheWeekUiModel.SAT] = binding.checkboxSat

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
