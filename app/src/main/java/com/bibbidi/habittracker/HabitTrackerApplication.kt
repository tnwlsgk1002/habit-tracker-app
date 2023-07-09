package com.bibbidi.habittracker

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.provider.FontRequest
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.FontRequestEmojiCompatConfig
import com.bibbidi.habittracker.ui.common.Constants.START_HABIT_CHANNEL_ID
import com.jakewharton.threetenabp.AndroidThreeTen
import com.vanniktech.emoji.EmojiManager
import com.vanniktech.emoji.googlecompat.GoogleCompatEmojiProvider
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HabitTrackerApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initThreeTen()
        initEmojiManager()
        createNotificationChannel()
    }

    private fun initThreeTen() {
        AndroidThreeTen.init(this)
    }

    private fun initEmojiManager() {
        EmojiManager.install(
            GoogleCompatEmojiProvider(
                EmojiCompat.init(
                    FontRequestEmojiCompatConfig(
                        this,
                        FontRequest(
                            "com.google.android.gms.fonts",
                            "com.google.android.gms",
                            "Noto Color Emoji Compat",
                            R.array.com_google_android_gms_fonts_certs
                        )
                    ).setReplaceAll(true)
                )
            )
        )
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.start_habit_channel_name)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(START_HABIT_CHANNEL_ID, name, importance)
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
