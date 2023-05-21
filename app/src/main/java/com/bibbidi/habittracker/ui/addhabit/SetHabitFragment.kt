package com.bibbidi.habittracker.ui.addhabit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.FragmentSetHabitBinding
import com.bibbidi.habittracker.ui.addhabit.dialog.EmojiPickerBottomSheetDialogFragment
import com.bibbidi.habittracker.ui.addhabit.dialog.GoalTimePickerBottomSheetDialogFragment
import com.bibbidi.habittracker.ui.addhabit.dialog.RepeatDayOfTheWeeksPickerBottomSheetDialogFragment
import com.bibbidi.habittracker.ui.addhabit.dialog.WhenRunInputBottomSheetDialogFragment
import com.bibbidi.habittracker.ui.model.DayOfTheWeek
import com.bibbidi.habittracker.utils.asLocalDate
import com.bibbidi.habittracker.utils.asLong
import com.bibbidi.habittracker.utils.toGoalTimeString
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import org.threeten.bp.Duration
import org.threeten.bp.LocalTime

@AndroidEntryPoint
class SetHabitFragment : Fragment() {

    companion object {
        const val EMOJI_PICKER_TAG = "emojiPicker"
        const val GOAL_TIME_PICKER_TAG = "goalTimePicker"
        const val REPEAT_DAY_OF_THE_WEEKS_TAG = "repeatDayOfTheWeeksPicker"
        const val WHEN_RUN_INPUT_TAG = "whenRunInput"
        const val START_DATE_PICKER_TAG = "startDatePicker"
        const val ALARM_TIME_PICKER_TAR = "alarmTimePicker"
    }

    private val viewModel: SetHabitViewModel by viewModels()

    private var _binding: FragmentSetHabitBinding? = null

    private val binding get() = _binding!!

    private lateinit var emojiPickerBottomSheetDialogFragment: EmojiPickerBottomSheetDialogFragment
    private lateinit var goalTimePickerBottomSheetDialogFragment: GoalTimePickerBottomSheetDialogFragment
    private lateinit var repeatDayOfTheWeeksPickerBottomSheetDialogFragment: RepeatDayOfTheWeeksPickerBottomSheetDialogFragment
    private lateinit var whenRunInputBottomSheetDialogFragment: WhenRunInputBottomSheetDialogFragment

    private val selectStartDatePicker by lazy {
        val constraintsBuilder =
            CalendarConstraints.Builder()
                .setStart(System.currentTimeMillis())
                .setValidator(DateValidatorPointForward.now())
        val materialDatePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.select_start_date))
            .setSelection(viewModel.startDate.value?.asLong())
            .setCalendarConstraints(constraintsBuilder.build())
            .build()

        materialDatePicker.addOnPositiveButtonClickListener {
            viewModel.startDate.value = it.asLocalDate()
        }

        materialDatePicker
    }

    private val alarmTimePicker by lazy {
        val nowLocalTime = LocalTime.now()
        val materialTimePicker = MaterialTimePicker.Builder()
            .setInputMode(INPUT_MODE_KEYBOARD)
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(nowLocalTime.hour)
            .setMinute(nowLocalTime.minute)
            .setTitleText(getString(R.string.input_alarm_time)).build()

        materialTimePicker.addOnPositiveButtonClickListener {
            val hourOfPromise = materialTimePicker.hour
            val minuteOfPromise = materialTimePicker.minute
            viewModel.alarmTime.value = LocalTime.of(hourOfPromise, minuteOfPromise)
        }

        materialTimePicker
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        activity?.setTheme(R.style.Theme_HabitTracker_Other)

        (activity as AppCompatActivity).supportActionBar?.apply {
            title = ""
        }?.show()

        _binding = FragmentSetHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBottomSheet()
        setEventListener()
        observeData()
    }

    private fun initBottomSheet() {
        emojiPickerBottomSheetDialogFragment =
            EmojiPickerBottomSheetDialogFragment.newInstance { emoji ->
                viewModel.emoji.value = emoji.unicode
                emojiPickerBottomSheetDialogFragment.dismiss()
            }
        goalTimePickerBottomSheetDialogFragment =
            GoalTimePickerBottomSheetDialogFragment.newInstance(
                viewModel.goalTime.value?.toHoursPart() ?: 0,
                viewModel.goalTime.value?.toMillisPart() ?: 0
            ) { hour, minute ->
                viewModel.goalTime.value =
                    Duration.ofHours(hour.toLong()).plusMinutes(minute.toLong())
            }
        repeatDayOfTheWeeksPickerBottomSheetDialogFragment =
            RepeatDayOfTheWeeksPickerBottomSheetDialogFragment.newInstance(
                viewModel.repeatsDayOfTheWeeks.value ?: DayOfTheWeek.values().toSet()
            ) { dayOfTheWeeks -> viewModel.repeatsDayOfTheWeeks.value = dayOfTheWeeks }
        whenRunInputBottomSheetDialogFragment = WhenRunInputBottomSheetDialogFragment.newInstance(
            viewModel.whenRun.value ?: ""
        ) { whenRun -> viewModel.whenRun.value = whenRun }
    }

    private fun setEventListener() {
        binding.buttonSetEmoji.setOnClickListener {
            emojiPickerBottomSheetDialogFragment.show(parentFragmentManager, EMOJI_PICKER_TAG)
        }
        binding.switchAlarm.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                true -> alarmTimePicker.show(parentFragmentManager, ALARM_TIME_PICKER_TAR)
                false -> viewModel.alarmTime.value = null
            }
        }
        binding.buttonSetWhen.setOnClickListener {
            whenRunInputBottomSheetDialogFragment.show(parentFragmentManager, WHEN_RUN_INPUT_TAG)
        }
        binding.buttonSetRepeatsDayOfTheWeeks.setOnClickListener {
            repeatDayOfTheWeeksPickerBottomSheetDialogFragment.show(
                parentFragmentManager,
                REPEAT_DAY_OF_THE_WEEKS_TAG
            )
        }
        binding.buttonSetGoalTime.setOnClickListener {
            goalTimePickerBottomSheetDialogFragment.show(
                parentFragmentManager,
                GOAL_TIME_PICKER_TAG
            )
        }
        binding.buttonSetStartDate.setOnClickListener {
            selectStartDatePicker.show(parentFragmentManager, START_DATE_PICKER_TAG)
        }
        binding.buttonSubmit.setOnClickListener {
            viewModel.setHabit()
        }
    }

    private fun observeData() {
        viewModel.name.observe(viewLifecycleOwner) {
            binding.editTextName.setText(it)
        }
        viewModel.emoji.observe(viewLifecycleOwner) {
            binding.buttonSetEmoji.contentText = it
        }
        viewModel.alarmTime.observe(viewLifecycleOwner) {
            binding.switchAlarm.isChecked = (it != null)
            binding.switchAlarm.text = it?.toString() ?: ""
        }
        viewModel.whenRun.observe(viewLifecycleOwner) {
            binding.buttonSetWhen.contentText = it
        }
        viewModel.repeatsDayOfTheWeeks.observe(viewLifecycleOwner) { dayOfTheWeeks ->
            binding.buttonSetRepeatsDayOfTheWeeks.contentText =
                dayOfTheWeeks.joinToString(separator = ",") {
                    requireContext().getString(it.resId)
                }
        }
        viewModel.goalTime.observe(viewLifecycleOwner) {
            binding.buttonSetGoalTime.contentText = it.toGoalTimeString()
        }
        viewModel.startDate.observe(viewLifecycleOwner) {
            binding.buttonSetStartDate.contentText = it.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
