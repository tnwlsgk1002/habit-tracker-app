package com.bibbidi.habittracker.ui.common.parcer

import android.os.Parcel
import kotlinx.parcelize.Parceler
import org.threeten.bp.DayOfWeek

object DayOfWeekSetParceler : Parceler<Set<DayOfWeek>> {
    override fun create(parcel: Parcel): Set<DayOfWeek> {
        val size = parcel.readInt()
        val array = Array(size) { " " }
        parcel.readStringArray(array)
        return array.map { enumValueOf<DayOfWeek>(it) }.toSet()
    }

    override fun Set<DayOfWeek>.write(parcel: Parcel, flags: Int) {
        parcel.writeInt(size)
        parcel.writeStringArray(map { it.name }.toTypedArray())
    }
}
