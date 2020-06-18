package com.arelig.dalia.dbmodel

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.arelig.dalia.datamodel.Plant
import java.util.*

class HousePlantDBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
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
    fun addHousePlant(plant: Plant): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContractHousePlant.HousePlantEntry.COLUMN_ID, plant.id)
        values.put(DBContractHousePlant.HousePlantEntry.COLUMN_NAME, plant.name)
        values.put(DBContractHousePlant.HousePlantEntry.COLUMN_CATEGORY, plant.category)

        // Insert the new row, returning the primary key value of the new row
        db.insert(DBContractHousePlant.HousePlantEntry.TABLE_NAME, null, values)

        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun removeHousePlant(id: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContractHousePlant.HousePlantEntry.COLUMN_ID + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(id)
        // Issue SQL statement.
        db.delete(DBContractHousePlant.HousePlantEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }

    fun getHousePlant(id: String): ArrayList<Plant> {
        val housePlants = ArrayList<Plant>()
        val db = writableDatabase
        var cursor: Cursor?
        try {
            cursor = db.rawQuery(
                "select * from " + DBContractHousePlant.HousePlantEntry.TABLE_NAME + " WHERE " + DBContractHousePlant.HousePlantEntry.COLUMN_ID + "='" + id + "'",
                null
            )
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var name: String
        var category: String
        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                name =
                    cursor.getString(cursor.getColumnIndex(DBContractHousePlant.HousePlantEntry.COLUMN_NAME))
                category =
                    cursor.getString(cursor.getColumnIndex(DBContractHousePlant.HousePlantEntry.COLUMN_CATEGORY))
                housePlants.add(
                    Plant(
                        id,
                        name,
                        category
                    )
                )
                cursor.moveToNext()
            }
        }
        return housePlants
    }

    fun getAllHousePlants(): ArrayList<Plant> {
        val housePlants = ArrayList<Plant>()
        val db = writableDatabase
        var cursor: Cursor?
        try {
            cursor = db.rawQuery(
                "select * from " + DBContractHousePlant.HousePlantEntry.TABLE_NAME,
                null
            )
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var id: String
        var name: String
        var category: String
        if (cursor!!.moveToFirst()) {
            while (!cursor.isAfterLast) {
                id =
                    cursor.getString(cursor.getColumnIndex(DBContractHousePlant.HousePlantEntry.COLUMN_ID))
                name =
                    cursor.getString(cursor.getColumnIndex(DBContractHousePlant.HousePlantEntry.COLUMN_NAME))
                category =
                    cursor.getString(cursor.getColumnIndex(DBContractHousePlant.HousePlantEntry.COLUMN_CATEGORY))

                housePlants.add(
                    Plant(
                        id,
                        name,
                        category
                    )
                )
                cursor.moveToNext()
            }
        }
        return housePlants
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "FeedReader.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContractHousePlant.HousePlantEntry.TABLE_NAME + " (" +
                    DBContractHousePlant.HousePlantEntry.COLUMN_ID + " TEXT PRIMARY KEY, " +
                    DBContractHousePlant.HousePlantEntry.COLUMN_NAME + " TEXT, " +
                    DBContractHousePlant.HousePlantEntry.COLUMN_CATEGORY + " TEXT) "

        private val SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DBContractHousePlant.HousePlantEntry.TABLE_NAME
    }
}