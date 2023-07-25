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
import com.bibbidi.habittracker.ui.common.Constants
import com.bibbidi.habittracker.ui.common.SendEventListener
import com.bibbidi.habittracker.ui.common.delegate.viewBinding
import com.bibbidi.habittracker.ui.common.dialog.EmojiPickerBottomSheet
import com.bibbidi.habittracker.ui.common.isAlreadyGranted
import com.bibbidi.habittracker.ui.common.isRationale
import com.bibbidi.habittracker.ui.model.habit.HabitUiModel
import com.bibbidi.habittracker.utils.repeatOnStarted
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import org.threeten.bp.LocalTime
import javax.inject.Inject

@AndroidEntryPoint
class UpdateHabitActivity : AppCompatActivity(), SendEventListener<HabitUiModel> {

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

    private val alarmTimePicker: MaterialTimePicker by lazy {
        val nowLocalTime = LocalTime.now()
        MaterialTimePicker.Builder().setInputMode(MaterialTimePicker.INPUT_MODE_KEYBOARD)
            .setTimeFormat(TimeFormat.CLOCK_24H).setHour(nowLocalTime.hour)
            .setMinute(nowLocalTime.minute).setTitleText(getString(R.string.input_alarm_time))
            .build().apply {
                addOnPositiveButtonClickListener {
                    viewModel.setAlarmTime(LocalTime.of(hour, minute))
                }
                isCancelable = false
            }
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
            viewModel.event.collectLatest {
                when (it) {
                    UpdateHabitEvent.EmojiClickedEvent -> showEmojiBottomSheet()
                    UpdateHabitEvent.AlarmTimeClickedEvent -> checkNotificationPermission()
                    is UpdateHabitEvent.SubmitEvent -> sendEvent(it.habitInfo)
                }
            }
        }
    }

    override fun sendEvent(value: HabitUiModel) {
        val resultIntent = Intent()
        val bundle = Bundle().apply {
            putParcelable(Constants.HABIT_INFO_KEY, value)
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

    private fun openAppSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            .setData(Uri.parse("package:${BuildConfig.APPLICATION_ID}"))
        startActivity(intent)
    }
}
