package com.arelig.dalia.dbmodel

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.arelig.dalia.datamodel.Plant

import java.util.ArrayList

class PlantDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertPlant(plant: Plant): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContractPlant.UserEntry.COLUMN_ID, plant.plantId)
        values.put(DBContractPlant.UserEntry.COLUMN_NAME, plant.name)
        values.put(DBContractPlant.UserEntry.COLUMN_CATEGORY, plant.category)
        values.put(DBContractPlant.UserEntry.COLUMN_WATERING_FREQ, plant.wateringFreq)

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(DBContractPlant.UserEntry.TABLE_NAME, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deletePlant(plantid: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContractPlant.UserEntry.COLUMN_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(plantid)
        // Issue SQL statement.
        db.delete(DBContractPlant.UserEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readPlant(plantid: String): ArrayList<Plant> {
        val plants = ArrayList<Plant>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContractPlant.UserEntry.TABLE_NAME + " WHERE " + DBContractPlant.UserEntry.COLUMN_ID + "='" + plantid + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var name: String
        var category : Int
        var wateringFreq: Int
        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                name = cursor.getString(cursor.getColumnIndex(DBContractPlant.UserEntry.COLUMN_NAME))
                wateringFreq = cursor.getInt(cursor.getColumnIndex(DBContractPlant.UserEntry.COLUMN_WATERING_FREQ))
                category = cursor.getInt(cursor.getColumnIndex(DBContractPlant.UserEntry.COLUMN_CATEGORY))
                plants.add(
                    Plant(
                        plantid,
                        name,
                        category,
                        wateringFreq
                    )
                )
                cursor.moveToNext()
            }
        }
        return plants
    }

    fun readAllPlants(): ArrayList<Plant> {
        val plants = ArrayList<Plant>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContractPlant.UserEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var plantid: String
        var name: String
        var category : Int
        var wateringFreq: Int
        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                plantid = cursor.getString(cursor.getColumnIndex(DBContractPlant.UserEntry.COLUMN_ID))
                name = cursor.getString(cursor.getColumnIndex(DBContractPlant.UserEntry.COLUMN_NAME))
                category = cursor.getInt(cursor.getColumnIndex(DBContractPlant.UserEntry.COLUMN_CATEGORY))
                wateringFreq = cursor.getInt(cursor.getColumnIndex(DBContractPlant.UserEntry.COLUMN_WATERING_FREQ))

                plants.add(
                    Plant(
                        plantid,
                        name,
                        category,
                        wateringFreq
                    )
                )
                cursor.moveToNext()
            }
        }
        return plants
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "FeedReader.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContractPlant.UserEntry.TABLE_NAME + " (" +
                    DBContractPlant.UserEntry.COLUMN_ID + " TEXT PRIMARY KEY," +
                    DBContractPlant.UserEntry.COLUMN_NAME + " TEXT," +
                    DBContractPlant.UserEntry.COLUMN_CATEGORY + "TEXT," +
                    DBContractPlant.UserEntry.COLUMN_WATERING_FREQ+ " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContractPlant.UserEntry.TABLE_NAME
    }
}