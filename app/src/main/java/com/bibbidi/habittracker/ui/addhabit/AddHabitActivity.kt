package com.bibbidi.habittracker.ui.addhabit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bibbidi.habittracker.R
import com.bibbidi.habittracker.databinding.ActivityAddHabitBinding
import com.bibbidi.habittracker.ui.addhabit.check.AddCheckHabitFragment
import com.bibbidi.habittracker.ui.addhabit.time.AddTimeHabitFragment
import com.bibbidi.habittracker.ui.addhabit.track.AddTrackHabitFragment
import com.bibbidi.habittracker.ui.common.Constants.HABIT_INFO_KEY
import com.bibbidi.habittracker.ui.common.Constants.HABIT_TYPE_KEY
import com.bibbidi.habittracker.ui.common.SendEventListener
import com.bibbidi.habittracker.ui.common.viewBinding
import com.bibbidi.habittracker.ui.model.habit.HabitTypeUiModel
import com.bibbidi.habittracker.ui.model.habit.habitinfo.HabitInfoUiModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddHabitActivity : AppCompatActivity(), SendEventListener<HabitInfoUiModel> {

    private val binding by viewBinding(ActivityAddHabitBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = ""
        }
        initFragment()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initFragment() {
        val fragment = when (intent?.extras?.getParcelable<HabitTypeUiModel>(HABIT_TYPE_KEY)) {
            HabitTypeUiModel.CheckType -> AddCheckHabitFragment()
            HabitTypeUiModel.TimeType -> AddTimeHabitFragment()
            HabitTypeUiModel.TrackType -> AddTrackHabitFragment()
            else -> null
        }?.apply { submitEventListener = this@AddHabitActivity }

        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }

    override fun sendEvent(value: HabitInfoUiModel) {
        val resultIntent = Intent()
        val bundle = Bundle().apply {
            putParcelable(HABIT_INFO_KEY, value)
        }
        resultIntent.putExtras(bundle)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}
