package com.bibbidi.habittracker.ui.sethabit.updatehabit

import android.Manifest.permission.POST_NOTIFICATIONS
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
import com.bibbidi.habittracker.databinding.ActivityUpdateHabitBinding
import com.bibbidi.habittracker.ui.common.delegate.viewBinding
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
import org.threeten.bp.LocalTime
import javax.inject.Inject

@AndroidEntryPoint
class UpdateHabitActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityUpdateHabitBinding::inflate)

    @Inject
    lateinit var viewModelFactory: UpdateHabitViewModel.HabitInfoAssistedFactory

    private fun getHabitInfo() =
        intent?.extras?.getParcelable<HabitUiModel>(Constants.HABIT_INFO_KEY)

    private val viewModel by viewModels<UpdateHabitViewModel> {
        val habitInfo = getHabitInfo() ?: error("habitInfo is null")
        UpdateHabitViewModel.provideFactory(viewModelFactory, habitInfo)
    }

    private val emojiPickerBottomSheet: EmojiPickerBottomSheet by lazy {
        EmojiPickerBottomSheet.newInstance { emoji ->
            viewModel.setEmoji(emoji.unicode)
            emojiPickerBottomSheet.dismiss()
        }
    }

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

    private val requestNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            showAlarmTimeDialog()
        } else {
            showPermissionDenySnackBar()
        }
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
        binding.lifecycleOwner = this
        binding.vm = viewModel
    }

    private fun collectEvent() {
        repeatOnStarted {
            viewModel.event.collectLatest { event ->
                when (event) {
                    UpdateHabitEvent.EmojiClickedEvent -> showEmojiBottomSheet()
                    UpdateHabitEvent.AlarmTimeClickedEvent -> checkNotificationPermission()
                    UpdateHabitEvent.ShowLeastOneSelectedTimeFilterEvent -> showLeastOnSelectedTimeFilterSnackBar()
                    UpdateHabitEvent.ShowColorPickerEvent -> showColorPicker()
                    is UpdateHabitEvent.SubmitEvent -> submit(event.habit)
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

    private fun showEmojiBottomSheet() {
        emojiPickerBottomSheet.show(supportFragmentManager, Constants.EMOJI_PICKER_TAG)
    }

    private fun showAlarmTimeDialog() {
        alarmTimePicker.show(supportFragmentManager, Constants.ALARM_TIME_PICKER_TAG)
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                isAlreadyGranted(POST_NOTIFICATIONS) -> showAlarmTimeDialog()
                isRationale(POST_NOTIFICATIONS) -> showRationaleAlertDialog()
                else -> requestNotificationPermissionLauncher.launch(POST_NOTIFICATIONS)
            }
        } else {
            showAlarmTimeDialog()
        }
    }

    private fun showPermissionDenySnackBar() {
        Snackbar.make(
            binding.root,
            getString(R.string.permission_notification_deny_message),
            Snackbar.LENGTH_SHORT
        ).show()
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

    private fun openAppSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            .setData(Uri.parse("package:${BuildConfig.APPLICATION_ID}"))
        startActivity(intent)
    }
}
