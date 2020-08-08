package com.arelig.dalia.dbmodel

import android.content.Context
import com.arelig.dalia.datamodel.Plant

/*
    A db controller uses a db helper, which is used as an adapter to the db
 */
object DBHousePlantController {
    private var dbHelper: DBHousePlantHelper? = null

    fun getInstance(context: Context): DBHousePlantHelper {
        if (dbHelper == null) {
            dbHelper = DBHousePlantHelper(context)
        }

        return dbHelper!!
    }

    fun addHousePlant(name: String, category: String) {
            dbHelper?.addHousePlant(Plant(name, name, category))
        }
    }