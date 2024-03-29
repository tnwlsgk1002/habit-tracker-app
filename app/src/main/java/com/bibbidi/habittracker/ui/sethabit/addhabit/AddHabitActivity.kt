package com.bibbidi.habittracker.ui.sethabit.addhabit

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bibbidi.habittracker.BuildConfig
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.ActivityAddHabitBinding
import com.bibbidi.habittracker.ui.common.delegate.viewBinding
import com.bibbidi.habittracker.ui.common.dialog.DatePickerBottomSheet
import com.bibbidi.habittracker.ui.common.dialog.DayOfTheWeeksPickerBottomSheet
import com.bibbidi.habittracker.ui.common.dialog.EmojiPickerBottomSheet
import com.bibbidi.habittracker.ui.common.dialog.colorpicker.ColorPickerBottomSheet
import com.bibbidi.habittracker.ui.common.dialog.timepicker.TimePickerBottomSheet
import com.bibbidi.habittracker.ui.common.isAlreadyGranted
import com.bibbidi.habittracker.ui.common.isRationale
import com.bibbidi.habittracker.ui.model.habit.HabitUiModel
import com.bibbidi.habittracker.utils.Constants
import com.bibbidi.habittracker.utils.Constants.HABIT_INFO_KEY
import com.bibbidi.habittracker.utils.repeatOnStarted
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import javax.inject.Inject

@AndroidEntryPoint
class AddHabitActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityAddHabitBinding::inflate)

    @Inject
    lateinit var viewModelFactory: AddHabitViewModel.HabitInfoAssistedFactory

    private fun getHabitInfo() =
        intent?.extras?.getParcelable<HabitUiModel>(Constants.HABIT_INFO_KEY)

    private val viewModel by viewModels<AddHabitViewModel> {
        val habitInfo = getHabitInfo() ?: error("HabitInfo is null")
        AddHabitViewModel.provideFactory(viewModelFactory, habitInfo)
    }

    private val requestNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            showAlarmTimePicker()
        } else {
            showPermissionDenySnackBar()
        }
    }

    private val emojiPickerBottomSheet: EmojiPickerBottomSheet by lazy {
        EmojiPickerBottomSheet.newInstance { emoji ->
            viewModel.setEmoji(emoji.unicode)
            emojiPickerBottomSheet.dismiss()
        }
    }

    private val dayOfTheWeeksPickerBottomSheet: DayOfTheWeeksPickerBottomSheet
        get() = DayOfTheWeeksPickerBottomSheet.newInstance(
            viewModel.repeatsDayOfTheWeeksFlow.value
        ) { dayOfTheWeeks ->
            viewModel.setRepeatsDayOfTheWeeks(dayOfTheWeeks)
        }

    private val startDatePicker: DatePickerBottomSheet
        get() = DatePickerBottomSheet.newInstance(
            viewModel.startDateFlow.value,
            minDate = LocalDate.now(),
            onPositiveListener = { date -> viewModel.setStartDate(date) }
        )

    private val alarmTimePicker: TimePickerBottomSheet
        get() = TimePickerBottomSheet.newInstance(
            viewModel.alarmTimeFlow.value ?: LocalTime.now()
        ) { time ->
            viewModel.setAlarmTime(time)
        }

    private val colorPicker: ColorPickerBottomSheet
        get() = ColorPickerBottomSheet.newInstance(
            viewModel.colorFlow.value
        ) {
            viewModel.setColor(it)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            title = ""
        }
        initBindingData()
        collectEvent()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initBindingData() {
        binding.vm = viewModel
        binding.lifecycleOwner = this
    }

    private fun collectEvent() {
        repeatOnStarted {
            viewModel.event.collectLatest { event ->
                when (event) {
                    AddHabitEvent.EmojiClickedEvent -> showEmojiPicketBottomSheet()
                    AddHabitEvent.AlarmTimeClickedEvent -> checkNotificationPermission()
                    AddHabitEvent.RepeatsDayOfTheWeekClickEvent -> showDayOfTheWeeksPickerBottomSheet()
                    AddHabitEvent.StartDateClickEvent -> showSelectStartDatePicker()
                    AddHabitEvent.StartDateIsBeforeNowEvent -> showStartDateIsBeforeNowSnackBar()
                    AddHabitEvent.ShowLeastOneSelectedTimeFilterEvent -> showLeastOnSelectedTimeFilterSnackBar()
                    AddHabitEvent.ShowColorPickerEvent -> showColorPicker()
                    is AddHabitEvent.SubmitEvent -> submit(event.habit)
                }
            }
        }
    }

    private fun submit(habit: HabitUiModel) {
        val resultIntent = Intent()
        val bundle = Bundle().apply {
            putParcelable(HABIT_INFO_KEY, habit)
        }
        resultIntent.putExtras(bundle)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    private fun showEmojiPicketBottomSheet() {
        emojiPickerBottomSheet.show(supportFragmentManager, Constants.EMOJI_PICKER_TAG)
    }

    private fun showAlarmTimePicker() {
        alarmTimePicker.show(supportFragmentManager, Constants.ALARM_TIME_PICKER_TAG)
    }

    private fun showDayOfTheWeeksPickerBottomSheet() {
        dayOfTheWeeksPickerBottomSheet.show(
            supportFragmentManager,
            Constants.REPEAT_DAY_OF_THE_WEEKS_TAG
        )
    }

    private fun showSelectStartDatePicker() {
        startDatePicker.show(supportFragmentManager, Constants.START_DATE_PICKER_TAG)
    }

    private fun showStartDateIsBeforeNowSnackBar() {
        Snackbar.make(
            binding.root,
            getString(R.string.start_date_is_before_now_error_message),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun showPermissionDenySnackBar() {
        Snackbar.make(
            binding.root,
            getString(R.string.permission_notification_deny_message),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun showLeastOnSelectedTimeFilterSnackBar() {
        Snackbar.make(
            binding.root,
            getString(R.string.least_on_selected_message),
            Snackbar.LENGTH_SHORT
        ).show()
    }

    private fun showColorPicker() {
        colorPicker.show(supportFragmentManager, Constants.COLOR_PICKER_TAG)
    }

    private fun checkNotificationPermission() {
        when {
            isAlreadyGranted(Manifest.permission.POST_NOTIFICATIONS) -> showAlarmTimePicker()
            isRationale(Manifest.permission.POST_NOTIFICATIONS) -> showRationaleAlertDialog()
            else -> {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    showRationaleAlertDialog()
                }
            }
        }
    }

    private fun showRationaleAlertDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.permission_notification_already_deny_message))
            .setMessage(getString(R.string.permission_notification_grated_in_setting_message))
            .setNeutralButton(getString(R.string.cancel)) { _, _ ->
            }.setPositiveButton(getString(R.string.setting)) { _, _ ->
                openAppSetting()
            }.show()
    }

    private fun openAppSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            .setData(Uri.parse("package:${BuildConfig.APPLICATION_ID}"))
        startActivity(intent)
    }
}
