package com.arelig.dalia.data

import com.google.firebase.database.PropertyName

data class Plant(
    @PropertyName("name") var name: String,
    @PropertyName("category") var category: String,
    @PropertyName("last-watered") var lastWatered: String
) {

    constructor() : this("", "", "")

    override fun equals(other: Any?): Boolean {
        return this == other
    }
}