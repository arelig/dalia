package com.arelig.dalia.data

import com.google.firebase.database.PropertyName

data class Plant(
    @PropertyName("name") var name: String,
    @PropertyName("category") var category: String
) {

    constructor() : this("", "")

    override fun equals(obj: Any?): Boolean {
        return if (obj is Plant) {
            equals(obj as Plant?)
        } else false
    }
}