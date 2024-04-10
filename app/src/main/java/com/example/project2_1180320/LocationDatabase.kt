package com.example.project2_1180320

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class LocationDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {
    companion object {
        const val DATABASE_NAME = "LocationDatabase"
        const val VERSION = 1
        const val TABLE_NAME = "Coordinate"
        const val COLUMN_ID = "id"
        const val COLUMN_LATITUDE = "latitude"
        const val COLUMN_LONGITUDE = "longitude"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query =
            "CREATE TABLE $TABLE_NAME($COLUMN_ID Integer Primary Key, $COLUMN_LATITUDE Text, $COLUMN_LONGITUDE Text)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val query = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(query)
        onCreate(db)
    }

    fun insert(data: Locations) {
        val db = writableDatabase

        val contentValue = ContentValues().apply {
            put(COLUMN_LATITUDE, data.latitude)
            put(COLUMN_LONGITUDE, data.longitude)
        }

        db.insert(TABLE_NAME, null, contentValue)
        db.close()
    }

    fun read(): ArrayList<Locations> {

        val locationsArrays = ArrayList<Locations>()

        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        try {
            while (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                val latitude = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LATITUDE))
                val longitude = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LONGITUDE))

                val location = Locations(id, latitude, longitude)
                locationsArrays.add(location)
            }
        } finally {
            cursor.close()
            db.close()
        }

        return locationsArrays
    }

}
