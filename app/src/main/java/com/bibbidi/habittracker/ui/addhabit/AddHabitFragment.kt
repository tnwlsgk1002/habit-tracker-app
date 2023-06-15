package com.bibbidi.habittracker.ui.addhabit

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewbinding.ViewBinding
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.ui.common.Constants.ALARM_TIME_PICKER_TAG
import com.bibbidi.habittracker.ui.common.Constants.EMOJI_PICKER_TAG
import com.bibbidi.habittracker.ui.common.Constants.REPEAT_DAY_OF_THE_WEEKS_TAG
import com.bibbidi.habittracker.ui.common.Constants.START_DATE_PICKER_TAG
import com.bibbidi.habittracker.ui.common.Constants.WHEN_RUN_INPUT_TAG
import com.bibbidi.habittracker.ui.common.SendEventListener
import com.bibbidi.habittracker.ui.common.dialog.DayOfTheWeeksPickerBottomSheet
import com.bibbidi.habittracker.ui.common.dialog.EmojiPickerBottomSheet
import com.bibbidi.habittracker.ui.common.dialog.WhenRunInputBottomSheet
import com.bibbidi.habittracker.ui.model.habit.habitinfo.HabitInfoUiModel
import com.bibbidi.habittracker.utils.asLocalDate
import com.bibbidi.habittracker.utils.asLong
import com.bibbidi.habittracker.utils.repeatOnStarted
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_KEYBOARD
import com.google.android.material.timepicker.TimeFormat
import kotlinx.coroutines.flow.collectLatest
import org.threeten.bp.LocalTime

abstract class AddHabitFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    open val viewModel: AddHabitViewModel by viewModels()

    abstract val binding: ViewBinding

    var submitEventListener: SendEventListener<HabitInfoUiModel>? = null

    private val emojiPickerBottomSheet: EmojiPickerBottomSheet by lazy {
        EmojiPickerBottomSheet.newInstance { emoji ->
            viewModel.setEmoji(emoji.unicode)
            emojiPickerBottomSheet.dismiss()
        }
    }

    private val dayOfTheWeeksPickerBottomSheet: DayOfTheWeeksPickerBottomSheet by lazy {
        DayOfTheWeeksPickerBottomSheet.newInstance(
            viewModel.repeatsDayOfTheWeeksFlow.value
        ) { dayOfTheWeeks ->
            viewModel.setRepeatsDayOfTheWeeks(dayOfTheWeeks)
        }
    }

    private val whenRunInputBottomSheetDialogFragment: WhenRunInputBottomSheet by lazy {
        WhenRunInputBottomSheet.newInstance(
            viewModel.whenRunFlow.value
        ) { whenRun -> viewModel.setWhenRun(whenRun) }
    }

    private val selectStartDatePicker: MaterialDatePicker<Long> by lazy {
        val constraintsBuilder = CalendarConstraints.Builder().setStart(System.currentTimeMillis())
            .setValidator(DateValidatorPointForward.now())
        MaterialDatePicker.Builder.datePicker().setTitleText(getString(R.string.select_start_date))
            .setSelection(viewModel.startDateFlow.value?.asLong())
            .setCalendarConstraints(constraintsBuilder.build()).build().apply {
                addOnPositiveButtonClickListener {
                    viewModel.setStartDate(it.asLocalDate())
                }
            }
    }

    private val alarmTimePicker: MaterialTimePicker by lazy {
        val nowLocalTime = LocalTime.now()
        MaterialTimePicker.Builder().setInputMode(INPUT_MODE_KEYBOARD)
            .setTimeFormat(TimeFormat.CLOCK_24H).setHour(nowLocalTime.hour)
            .setMinute(nowLocalTime.minute).setTitleText(getString(R.string.input_alarm_time))
            .build().apply {
                addOnPositiveButtonClickListener {
                    viewModel.setAlarmTime(LocalTime.of(hour, minute))
                }
                isCancelable = false
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBindingData()
        collectEvent()
    }

    abstract fun initBindingData()

    open fun collectEvent() {
        repeatOnStarted {
            viewModel.emojiClickEvent.collectLatest {
                emojiPickerBottomSheet.show(parentFragmentManager, EMOJI_PICKER_TAG)
            }
        }

        repeatOnStarted {
            viewModel.alarmClickEvent.collectLatest {
                alarmTimePicker.show(parentFragmentManager, ALARM_TIME_PICKER_TAG)
            }
        }

        repeatOnStarted {
            viewModel.whenRunClickEvent.collectLatest {
                whenRunInputBottomSheetDialogFragment.show(
                    parentFragmentManager,
                    WHEN_RUN_INPUT_TAG
                )
            }
        }

        repeatOnStarted {
            viewModel.repeatsDayOfTheWeeksClickEvent.collectLatest {
                dayOfTheWeeksPickerBottomSheet.show(
                    parentFragmentManager,
                    REPEAT_DAY_OF_THE_WEEKS_TAG
                )
            }
        }

        repeatOnStarted {
            viewModel.startDateClickEvent.collectLatest {
                selectStartDatePicker.show(parentFragmentManager, START_DATE_PICKER_TAG)
            }
        }

        repeatOnStarted {
            viewModel.submitEvent.collectLatest {
                submitEventListener?.sendEvent(it)
            }
        }

        repeatOnStarted {
            viewModel.messageEvent.collectLatest {
                val message = getString(R.string.start_date_is_before_now_error_message)
                Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}
