package com.arelig.dalia.dbmodel

import android.provider.BaseColumns

object DBContractUser {
    /* Inner class that defines the table contents */

    class UserEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "user-plants"
            val COLUMN_ID = "id"
            val COLUMN_NAME = "name"
            val COLUMN_CATEGORY = "category"
            val COLUMN_WATERING_FREQ = "watering-frequency"
        }
    }
}