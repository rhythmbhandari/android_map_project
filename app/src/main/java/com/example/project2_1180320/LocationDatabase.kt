package com.example.project2_1180320

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class LocationDatabase (val context: Context):SQLiteOpenHelper(context,DATABASENAME,null,VERSION){
    companion object{
        val DATABASENAME = "LocationDatabase"
        val VERSION = 1
        val TABLENAME = "Coordinate"
        val COLUMNID = "id"
        val COLUMNNAME1 = "latitude"
        val COLUMNNAME2="longitude"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val query = "CREATE TABLE $TABLENAME($COLUMNID Integer Primary Key, $COLUMNNAME1 Text, $COLUMNNAME2 Text)"
        p0?.execSQL(query)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val query = "DROP TABLE IF EXISTS $TABLENAME"
        p0?.execSQL(query)
        onCreate(p0)
    }

    fun insert(data:Locations){
        val db = writableDatabase

        val contentValue = ContentValues().apply {
            put(COLUMNNAME1,data.Latitude)
            put(COLUMNNAME2,data.Longitude)
        }

        db.insert(TABLENAME,null,contentValue)
        db.close()

    }
    fun read():ArrayList<Locations>{

        val locationsArrays = ArrayList<Locations>()

        val db = readableDatabase
        val query = "SELECT * FROM $TABLENAME"
        val cursor = db.rawQuery(query,null)

        while (cursor.moveToNext())
        {
            val id =cursor.getInt(cursor.getColumnIndexOrThrow(COLUMNID))
            val latitude = cursor.getString(cursor.getColumnIndexOrThrow(COLUMNNAME1))
            val longitude = cursor.getString(cursor.getColumnIndexOrThrow(COLUMNNAME2))

            val location = Locations(id,latitude,longitude)
            locationsArrays.add(location)
        }
        db.close()
        return locationsArrays

    }

}
