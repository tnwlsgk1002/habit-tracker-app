package com.bibbidi.habittracker.utils

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.threeten.bp.LocalDate

internal class DateKtTest {

    @Test
    fun correct_isSameWeek() {
        val date1 = LocalDate.of(2023, 6, 12)
        val date2 = LocalDate.of(2023, 6, 17)
        assertEquals(true, date1.isSameWeek(date2))
    }

    @Test
    fun correct_getStartOfTheWeek() {
        val date1 = LocalDate.of(2023, 6, 16)
        val date2 = LocalDate.of(2023, 6, 11)
        assertEquals(date2, date1.getStartOfTheWeek())
    }
}
