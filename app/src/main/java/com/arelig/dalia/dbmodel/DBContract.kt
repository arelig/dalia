package com.arelig.dalia.dbmodel

import android.provider.BaseColumns

object DBContract {
    /* Inner class that defines the table contents */

    class PlantEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "plant-library"
            val COLUMN_PLANT_ID = "id"
            val COLUMN_NAME = "name"
            val COLUMN_WATERING_FREQ = "wateringfrequency"
            val COLUMN_CATEGORY = "category"
        }
    }
}