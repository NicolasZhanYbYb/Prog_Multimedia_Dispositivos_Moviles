package com.nizhvi.practica3

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.time.Year
//Nicolas Zhan
class MyStudentsDBOpenHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    val TAG = "SQLite"
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "students.db"
        const val STUDENT_TABLE = "student"
        const val ID_COLUMN = "_id"
        const val NAME_COLUMN = "name"
        const val DAY_COLUMN = "day"
        const val MONTH_COLUMN = "month"
        const val YEAR_COLUMN = "year"
        const val MODALITY_COLUMN = "modality"
        const val CYCLE_COLUMN = "cycle"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            val createStudentTable = "CREATE TABLE $STUDENT_TABLE " +
                    "($ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$NAME_COLUMN TEXT, " +
                    "$DAY_COLUMN INTEGER, " +
                    "$MONTH_COLUMN INTEGER, " +
                    "$YEAR_COLUMN INTEGER, " +
                    "$MODALITY_COLUMN TEXT, " +
                    "$CYCLE_COLUMN TEXT)"

            db!!.execSQL(createStudentTable)
        } catch (e: SQLiteException) {
            Log.e("$TAG (onCreate)", e.message.toString())
        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        try {
            val dropStudentTable = "DROP TABLE IF EXISTS $STUDENT_TABLE"
            db!!.execSQL(dropStudentTable)
            onCreate(db)
        } catch (e: SQLiteException) {
            Log.e("$TAG (onUpgrade)", e.message.toString())
        }
    }

    fun addStudent(name: String, day: Int, month: Int, year: Int, modality: Modality, cycle: Cycle) {
        val cycleS = cycle.toString()
        val isPresencial: Boolean = Modality.PRESENCIAL == modality

        val data = ContentValues()
        data.put(NAME_COLUMN, name)
        data.put(DAY_COLUMN, day)
        data.put(MONTH_COLUMN, month)
        data.put(YEAR_COLUMN, year)
        data.put(MODALITY_COLUMN, isPresencial.toString())
        data.put(CYCLE_COLUMN, cycleS)

        val db = this.writableDatabase
        db.insert(STUDENT_TABLE, null, data)
        db.close()
    }

    fun delStudent(identifier: Int): Int {
        val args = arrayOf(identifier.toString())
        val db = this.writableDatabase
        val result = db.delete(STUDENT_TABLE, "$ID_COLUMN = ?", args)
        db.close()
        return result
    }

}