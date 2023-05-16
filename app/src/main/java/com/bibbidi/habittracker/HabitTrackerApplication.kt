package com.bibbidi.habittracker

import android.app.Application
import androidx.core.provider.FontRequest
import androidx.emoji.text.EmojiCompat
import androidx.emoji.text.FontRequestEmojiCompatConfig
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
}
