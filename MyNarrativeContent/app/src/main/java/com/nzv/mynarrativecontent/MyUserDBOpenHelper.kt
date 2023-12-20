package com.nzv.mynarrativecontent

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class MyUserDBOpenHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    val TAG = "SQLite"
    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "usuario.db"
        const val USER_TABLE = "usuario"
        const val ID_COLUMN = "_id"
        const val NAME_COLUMN = "name"
        const val HASH_COLUMN = "hash"
        const val BIRTHDAY_COLUMN = "birthday"
        const val AVATAR_COLUMN = "avatar"
        const val USER_COLUMN = "user"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            val createStudentTable = "CREATE TABLE $USER_TABLE " +
                    "($ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$NAME_COLUMN TEXT, " +
                    "$HASH_COLUMN INTEGER, " +
                    "$BIRTHDAY_COLUMN INTEGER, " +
                    "$AVATAR_COLUMN INTEGER, " +
                    "$USER_COLUMN TEXT)"

            db!!.execSQL(createStudentTable)
        } catch (e: SQLiteException) {
            Log.e("$TAG (onCreate)", e.message.toString())
        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        try {
            val dropStudentTable = "DROP TABLE IF EXISTS $USER_TABLE"
            db!!.execSQL(dropStudentTable)
            onCreate(db)
        } catch (e: SQLiteException) {
            Log.e("$TAG (onUpgrade)", e.message.toString())
        }
    }

    fun addStudent(name: String, user: String, hash: Int, avatar: String, birthday: Int) {
        val data = ContentValues()
        data.put(NAME_COLUMN, name)
        data.put(HASH_COLUMN, hash)
        data.put(USER_COLUMN, user)
        data.put(AVATAR_COLUMN, avatar)
        data.put(BIRTHDAY_COLUMN, birthday)

        val db = this.writableDatabase
        db.insert(USER_TABLE, null, data)
        db.close()
    }

    fun delStudent(identifier: Int): Int {
        val args = arrayOf(identifier.toString())
        val db = this.writableDatabase
        val result = db.delete(USER_TABLE, "$ID_COLUMN = ?", args)
        db.close()
        return result
    }
}