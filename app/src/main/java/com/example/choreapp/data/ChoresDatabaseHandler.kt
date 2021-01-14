package com.example.choreapp.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.choreapp.model.*
import java.text.DateFormat
import java.util.*


class ChoresDatabaseHandler(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {

        var CREATE_CHORE_TABLE = "CREATE TABLE" + TABLE_NAME + "(" + " INTEGER PRIMARY KEY," +
                KEY_CHORE_NAME + " TEXT," +
                KEY_CHORE_ASSIGNED_BY + " TEXT," +
                KEY_CHORE_ASSIGNED_TO + " TEXT," +
                KEY_CHORE_ASSIGNED_TIME + " LONG" +")"

        db?.execSQL(CREATE_CHORE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int){



        db?.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME )


        onCreate(db)


    }

    fun createChore(chore: Chore){
        var db: SQLiteDatabase=writableDatabase
        var values: ContentValues = ContentValues()
        values.put(KEY_CHORE_NAME, chore.choreName)
        values.put(KEY_CHORE_ASSIGNED_BY, chore.assignedBy)
        values.put(KEY_CHORE_ASSIGNED_TO, chore.assignedTo)
        values.put(KEY_CHORE_ASSIGNED_TIME, System.currentTimeMillis())

        db.insert(TABLE_NAME, null, values)

        Log.d("DATA INSERTED", "SUCCESS")
        db.close()
    }

    fun readAChore(id:Int): Chore{
        var db: SQLiteDatabase = writableDatabase
        var cursor: Cursor = db.query(TABLE_NAME, arrayOf(KEY_ID, KEY_CHORE_NAME, KEY_CHORE_ASSIGNED_BY, KEY_CHORE_ASSIGNED_TIME, KEY_CHORE_ASSIGNED_TO), KEY_ID+"=?",arrayOf(id.toString()),null,null,null,null)

        if(cursor !=null)
            cursor.moveToFirst()

        var chore = Chore()
        chore.choreName = cursor.getString(cursor.getColumnIndex(KEY_CHORE_NAME))
        chore.assignedTo= cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TO))
        chore.timeAssigned = cursor.getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))
        chore.assignedBy = cursor.getString(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_BY))

        var dateFormat: java.text.DateFormat = DateFormat.getDateInstance()
        var formattedDate = dateFormat.format(Date(cursor.
                getLong(cursor.getColumnIndex(KEY_CHORE_ASSIGNED_TIME))).time)

        return chore
    }



}