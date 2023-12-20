package com.nzv.mynarrativecontent

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class MyContentDBOpenHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    val TAG = "SQLite"
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "content.db"
        const val CONTENT_TABLE = "content"
        const val ID_COLUMN = "_id"
        const val NAME_COLUMN = "name"
        const val USER_COLUMN = "user"
        const val TYPE_COLUMN = "type"
        const val REVIEW_COLUMN = "review"
        const val FAVORITE_COLUMN = "favorite"
        const val CHAPTER_COLUMN = "chapter"
        const val POSTER_COLUMN = "poster"
        const val YEAR_COLUMN = "year"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            val createStudentTable = "CREATE TABLE $CONTENT_TABLE " +
                    "($ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$NAME_COLUMN TEXT, " +
                    "$USER_COLUMN TEXT, " +
                    "$TYPE_COLUMN TEXT, " +
                    "$REVIEW_COLUMN INTEGER, " +
                    "$FAVORITE_COLUMN TEXT, " +
                    "$CHAPTER_COLUMN INTEGER, " +
                    "$POSTER_COLUMN TEXT, " +
                    "$YEAR_COLUMN INTEGER)"

            db!!.execSQL(createStudentTable)
        } catch (e: SQLiteException) {
            Log.e("$TAG (onCreate)", e.message.toString())
        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        try {
            val dropStudentTable = "DROP TABLE IF EXISTS $CONTENT_TABLE"
            db!!.execSQL(dropStudentTable)
            onCreate(db)
        } catch (e: SQLiteException) {
            Log.e("$TAG (onUpgrade)", e.message.toString())
        }
    }

    fun addStudent(name: String, user: String, type: String, poster: String, review: String, favorite: Boolean, year: Int, chapter: Int) {
        val data = ContentValues()
        data.put(NAME_COLUMN, name)
        data.put(USER_COLUMN, user)
        data.put(TYPE_COLUMN, type)
        data.put(POSTER_COLUMN, poster)
        data.put(REVIEW_COLUMN, review)
        data.put(FAVORITE_COLUMN, favorite)
        data.put(YEAR_COLUMN, year)
        data.put(CHAPTER_COLUMN, chapter)

        val db = this.writableDatabase
        db.insert(CONTENT_TABLE, null, data)
        db.close()
    }

    fun delStudent(identifier: Int): Int {
        val args = arrayOf(identifier.toString())
        val db = this.writableDatabase
        val result = db.delete(CONTENT_TABLE, "$ID_COLUMN = ?", args)
        db.close()
        return result
    }
}