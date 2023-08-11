package com.bibbidi.habittracker.ui.common.dialog

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.BottomSheetInputRepeatDayOfTheWeeksBinding
import com.bibbidi.habittracker.ui.common.delegate.viewBinding
import com.bibbidi.habittracker.utils.Constants.DAY_OF_THE_WEEKS_KEY
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import org.threeten.bp.DayOfWeek

@AndroidEntryPoint
class DayOfTheWeeksPickerBottomSheet :
    BottomSheetDialogFragment(R.layout.bottom_sheet_input_repeat_day_of_the_weeks) {

    companion object {

        fun newInstance(
            dayOfTheWeeks: Set<DayOfWeek>,
            onSaveListener: (Set<DayOfWeek>) -> Unit
        ): DayOfTheWeeksPickerBottomSheet {
            val args = Bundle().apply {
                putStringArray(
                    DAY_OF_THE_WEEKS_KEY,
                    dayOfTheWeeks.map { it.name }.toTypedArray()
                )
            }

            println("만들었땅!")

            return DayOfTheWeeksPickerBottomSheet().apply {
                arguments = args
                setOnSaveListener(onSaveListener)
            }
        }
    }

    private val binding by viewBinding(BottomSheetInputRepeatDayOfTheWeeksBinding::bind)

    private var dayOfTheWeeks: Set<DayOfWeek> = DayOfWeek.values().toSet()

    private var onSaveListener: ((Set<DayOfWeek>) -> Unit)? = null

    private val dayOfTheWeekToView = mutableMapOf<DayOfWeek, CheckBox>()
    private val viewToDayOfTheWeek = mutableMapOf<CheckBox, DayOfWeek>()

    private val checkedDayOfTheWeeks
        get() = viewToDayOfTheWeek.filterKeys { it.isChecked }.values.toSet()

    fun setOnSaveListener(listener: ((Set<DayOfWeek>) -> Unit)?) {
        onSaveListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dayOfTheWeeks =
            arguments?.getStringArray(DAY_OF_THE_WEEKS_KEY)?.map { enumValueOf<DayOfWeek>(it) }
                ?.toSet()
                ?: DayOfWeek.values().toSet()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpMapData()
        setUpViewData()
    }

    private fun setUpMapData() {
        dayOfTheWeekToView.clear()
        viewToDayOfTheWeek.clear()

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
        for ((dayOfWeek, checkBox) in dayOfTheWeekToView) {
            checkBox.isChecked = dayOfTheWeeks.contains(dayOfWeek)

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked && dayOfTheWeekToView.values.none { it.isChecked }) {
                    showLeastOneSelectedSnackBar()
                    checkBox.isChecked = true
                }
            }
        }

        binding.run {
            btnSubmit.setOnClickListener {
                onSaveListener?.invoke(checkedDayOfTheWeeks)
                dismiss()
            }
            btnCancel.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun showLeastOneSelectedSnackBar() {
        Snackbar.make(
            binding.root,
            getString(R.string.least_on_selected_message),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        dismiss()
    }
}
