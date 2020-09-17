package com.arelig.dalia.data

data class Reminder(
    var name: String,
    val category: String,
    var wateringFreq: String,
    var lastWatered: String
) {

    constructor() : this("", "", "", "")

    override fun equals(other: Any?): Boolean {
        return this == other
    }
}