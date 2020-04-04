package com.arelig.dalia.dbmodel

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

import java.util.ArrayList

class UsersDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
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
        values.put(DBContract.PlantEntry.COLUMN_PLANT_ID, plant.plantId)
        values.put(DBContract.PlantEntry.COLUMN_NAME, plant.name)
        values.put(DBContract.PlantEntry.COLUMN_WATERING_FREQ, plant.wateringFreq)
        values.put(DBContract.PlantEntry.COLUMN_CATEGORY, plant.category)

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(DBContract.PlantEntry.TABLE_NAME, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteUser(plantid: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.PlantEntry.COLUMN_PLANT_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(plantid)
        // Issue SQL statement.
        db.delete(DBContract.PlantEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun readUser(plantid: String): ArrayList<Plant> {
        val plants = ArrayList<Plant>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.PlantEntry.TABLE_NAME + " WHERE " + DBContract.PlantEntry.COLUMN_PLANT_ID + "='" + plantid + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var name: String
        var wateringFreq: Int
        var category : Int
        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                name = cursor.getString(cursor.getColumnIndex(DBContract.PlantEntry.COLUMN_NAME))
                wateringFreq = cursor.getInt(cursor.getColumnIndex(DBContract.PlantEntry.COLUMN_WATERING_FREQ))
                category = cursor.getInt(cursor.getColumnIndex(DBContract.PlantEntry.COLUMN_CATEGORY))
                plants.add(Plant(plantid, name, wateringFreq, category))
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
            cursor = db.rawQuery("select * from " + DBContract.PlantEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var plantid: String
        var name: String
        var wateringFreq: Int
        var category : Int
        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                plantid = cursor.getString(cursor.getColumnIndex(DBContract.PlantEntry.COLUMN_PLANT_ID))
                name = cursor.getString(cursor.getColumnIndex(DBContract.PlantEntry.COLUMN_NAME))
                wateringFreq = cursor.getInt(cursor.getColumnIndex(DBContract.PlantEntry.COLUMN_WATERING_FREQ))
                category = cursor.getInt(cursor.getColumnIndex(DBContract.PlantEntry.COLUMN_CATEGORY))

                plants.add(Plant(plantid, name, wateringFreq, category))
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
            "CREATE TABLE " + DBContract.PlantEntry.TABLE_NAME + " (" +
                    DBContract.PlantEntry.COLUMN_PLANT_ID + " TEXT PRIMARY KEY," +
                    DBContract.PlantEntry.COLUMN_NAME + " TEXT," +
                    DBContract.PlantEntry.COLUMN_WATERING_FREQ + "TEXT," +
                    DBContract.PlantEntry.COLUMN_CATEGORY+ " TEXT)"

        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.PlantEntry.TABLE_NAME
    }
}