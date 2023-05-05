package com.bibbidi.habittracker.utils

import org.threeten.bp.LocalTime

fun LocalTime.toGoalTimeString(): String {
    return if (hour == 0 && minute == 0) {
        "0s"
    } else if (hour == 0) {
        "${minute}s"
    } else if (minute == 0) {
        "${hour}h"
    } else {
        "${hour}h ${minute}s"
    }
}
