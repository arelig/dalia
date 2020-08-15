package com.arelig.dalia.sqldatabase

import android.provider.BaseColumns

object DBContractHousePlant {
    /* Inner class that defines the table contents */

    class HousePlantEntry : BaseColumns {
        companion object {
            val TABLE_NAME = "houseplants"
            val COLUMN_ID = "id"
            val COLUMN_NAME = "name"
            val COLUMN_CATEGORY = "category"
        }
    }
}