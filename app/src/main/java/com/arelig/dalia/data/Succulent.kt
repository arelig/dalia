package com.arelig.dalia.data

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.Period

class Succulent(override var lastWatered: LocalDate? = null) : Category() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun wateringFrequency(): Period? {
        return Period.ofWeeks(2)
    }

    override fun lastWatered(): LocalDate? {
        return lastWatered
    }

    override fun lastWatered(value: LocalDate?) {
        lastWatered = value
    }
}