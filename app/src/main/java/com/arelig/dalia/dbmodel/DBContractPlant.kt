package com.arelig.dalia.dbmodel

import android.provider.BaseColumns

object DBContractPlant {
    /* Inner class that defines the table contents */

    class UserEntry : BaseColumns {
        //Almaceno toda la informacion de la planta porque aun no existe la tabla general
        companion object {
            val TABLE_NAME = "plant-library"
            val COLUMN_ID = "id"
            val COLUMN_NAME = "name"
            val COLUMN_CATEGORY = "category"
            val COLUMN_WATERING_FREQ = "watering-frequency"
        }
    }
}