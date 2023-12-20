package com.nzv.exam
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class MyAppDBOpenHelper(context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null, DATABASE_VERSION) {
    val TAG = "SQLite"
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "app.db"
        const val APP_TABLE = "app"
        const val ID_COLUMN = "_id"
        const val APLICACION_COLUMN = "aplicacion"
        const val TEMA_COLUMN = "tema"
        const val HORAS_COLUMN = "horas"
        const val MODALITY_COLUMN = "modalidad"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        try {
            val createStudentTable = "CREATE TABLE $APP_TABLE " +
                    "($ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$APLICACION_COLUMN TEXT, " +
                    "$TEMA_COLUMN TEXT, " +
                    "$HORAS_COLUMN TEXT, " +
                    "$MODALITY_COLUMN TEXT)"
            db!!.execSQL(createStudentTable)
        } catch (e: SQLiteException) {
            Log.e("$TAG (onCreate)", e.message.toString())
        }
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        try {
            val dropStudentTable = "DROP TABLE IF EXISTS $APP_TABLE"
            db!!.execSQL(dropStudentTable)
            onCreate(db)
        } catch (e: SQLiteException) {
            Log.e("$TAG (onUpgrade)", e.message.toString())
        }
    }
    fun addApp(app: String, tema: String, horas: String, modality: String) {
        val data = ContentValues()
        data.put(APLICACION_COLUMN, app)
        data.put(TEMA_COLUMN, tema)
        data.put(HORAS_COLUMN, horas)
        data.put(MODALITY_COLUMN, modality)
        val db = this.writableDatabase
        db.insert(APP_TABLE, null, data)
        db.close()
    }
}