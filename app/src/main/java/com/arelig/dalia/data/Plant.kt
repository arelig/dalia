package com.arelig.dalia.data

import android.widget.ImageView

/*
    THIS IS THE DATA MODEL
    @todo: activity logic only
 */

class Plant(val id: String, val name: String, val category: String) {
    lateinit var photo: ImageView

    override fun equals(obj: Any?): Boolean {
        return if (obj is Plant) {
            equals(obj as Plant?)
        } else false
    }
}