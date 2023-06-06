package com.bibbidi.habittracker.ui.common.parcer

import android.os.Parcel
import kotlinx.parcelize.Parceler
import org.threeten.bp.LocalDate

object LocalDateParceler : Parceler<LocalDate> {
    override fun create(parcel: Parcel): LocalDate {
        val year = parcel.readInt()
        val month = parcel.readInt()
        val day = parcel.readInt()
        return LocalDate.of(year, month, day)
    }

    override fun LocalDate.write(parcel: Parcel, flags: Int) {
        parcel.writeInt(year)
        parcel.writeInt(month.value)
        parcel.writeInt(dayOfMonth)
    }
}
