package com.bibbidi.habittracker.ui.common.parcer

import android.os.Parcel
import kotlinx.parcelize.Parceler
import org.threeten.bp.Duration

object DurationParceler : Parceler<Duration> {
    override fun create(parcel: Parcel): Duration {
        return Duration.ofSeconds(parcel.readLong())
    }

    override fun Duration.write(parcel: Parcel, flags: Int) {
        parcel.writeLong(seconds)
    }
}
