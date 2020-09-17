package com.arelig.dalia.data


import java.time.LocalDate
import java.time.Period

abstract class Category(open var lastWatered: LocalDate? = null) {
    abstract fun wateringFrequency(): Period?
    abstract fun lastWatered(): LocalDate?
    abstract fun lastWatered(value: LocalDate?)
}