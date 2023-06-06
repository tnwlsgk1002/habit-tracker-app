package com.bibbidi.habittracker.ui.common.parcer

import android.os.Parcel
import kotlinx.parcelize.Parceler
import org.threeten.bp.LocalTime

object LocalTimeParceler : Parceler<LocalTime> {
    override fun create(parcel: Parcel): LocalTime {
        val hour = parcel.readInt()
        val minute = parcel.readInt()
        val second = parcel.readInt()
        return LocalTime.of(hour, minute, second)
    }

    override fun LocalTime.write(parcel: Parcel, flags: Int) {
        parcel.writeInt(hour)
        parcel.writeInt(minute)
        parcel.writeInt(second)
    }
}
