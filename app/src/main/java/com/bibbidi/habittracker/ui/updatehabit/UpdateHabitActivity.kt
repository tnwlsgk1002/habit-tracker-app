package com.bibbidi.habittracker.ui.updatehabit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.ActivityUpdateHabitBinding
import com.bibbidi.habittracker.ui.common.Constants
import com.bibbidi.habittracker.ui.common.SendEventListener
import com.bibbidi.habittracker.ui.common.delegate.viewBinding
import com.bibbidi.habittracker.ui.common.dialog.EmojiPickerBottomSheet
import com.bibbidi.habittracker.ui.common.dialog.WhenRunInputBottomSheet
import com.bibbidi.habittracker.ui.model.habit.habitinfo.HabitInfoUiModel
import com.bibbidi.habittracker.utils.repeatOnStarted
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import org.threeten.bp.LocalTime
import javax.inject.Inject

@AndroidEntryPoint
class UpdateHabitActivity : AppCompatActivity(), SendEventListener<HabitInfoUiModel> {

    private val binding by viewBinding(ActivityUpdateHabitBinding::inflate)

    @Inject
    lateinit var viewModelFactory: UpdateHabitViewModel.HabitInfoAssistedFactory

    private fun getHabitInfo() =
        intent?.extras?.getParcelable<HabitInfoUiModel>(Constants.HABIT_INFO_KEY)

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

    private val whenRunInputBottomSheetDialogFragment: WhenRunInputBottomSheet by lazy {
        WhenRunInputBottomSheet.newInstance(
            viewModel.whenRunFlow.value
        ) { whenRun -> viewModel.setWhenRun(whenRun) }
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = ""
        }
        initBindingData()
        collectEvent()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initBindingData() {
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel
    }

    private fun collectEvent() {
        repeatOnStarted {
            viewModel.event.collectLatest {
                when (it) {
                    UpdateHabitEvent.EmojiClickedEvent -> showEmojiBottomSheet()
                    UpdateHabitEvent.AlarmTimeClickedEvent -> showAlarmTimeDialog()
                    UpdateHabitEvent.WhenRunClickedEvent -> showWhenRunInputBottomSheet()
                    is UpdateHabitEvent.SubmitEvent -> sendEvent(it.habitInfo)
                }
            }
        }
    }

    override fun sendEvent(value: HabitInfoUiModel) {
        val resultIntent = Intent()
        val bundle = Bundle().apply {
            putParcelable(Constants.HABIT_INFO_KEY, value)
        }
        resultIntent.putExtras(bundle)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

    private fun showWhenRunInputBottomSheet() {
        whenRunInputBottomSheetDialogFragment.show(
            supportFragmentManager,
            Constants.WHEN_RUN_INPUT_TAG
        )
    }

    private fun showEmojiBottomSheet() {
        emojiPickerBottomSheet.show(supportFragmentManager, Constants.EMOJI_PICKER_TAG)
    }

    private fun showAlarmTimeDialog() {
        alarmTimePicker.show(supportFragmentManager, Constants.ALARM_TIME_PICKER_TAG)
    }
}
