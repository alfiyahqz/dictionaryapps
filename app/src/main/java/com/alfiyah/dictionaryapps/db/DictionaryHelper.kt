package com.alfiyah.dictionaryapps.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.alfiyah.dictionaryapps.db.DatabaseContract.DictionaryColumns.Companion.DIC_ID
import com.alfiyah.dictionaryapps.db.DatabaseContract.DictionaryColumns.Companion.TABLE_NAME
import com.alfiyah.dictionaryapps.db.DatabaseContract.DictionaryColumns.Companion._ID

class DictionaryHelper(context: Context) {
    private var databaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME

        private var INSTANCE: DictionaryHelper? = null
        fun getInstance(context: Context): DictionaryHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: DictionaryHelper(context)
            }
    }

    @Throws(SQLException::class)
    fun open() {
        database = databaseHelper.writableDatabase
    }

    fun close() {
        databaseHelper.close()
        if (database.isOpen)
            database.close()
    }

    fun queryAll(): Cursor {
        return database.query(DATABASE_TABLE, null, null, null, null, null, "$_ID ASC")
    }

    fun insert(values: ContentValues?): Long {
        return database.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return database.update(DATABASE_TABLE, values, "$DIC_ID = ?", arrayOf(id))
    }

    fun deleteById(id: String): Int {
        return database.delete(DATABASE_TABLE, "$DIC_ID = '$id'", null)
    }

    fun isExists(id: String): Boolean {
        val sql = "SELECT EXISTS (SELECT * FROM $DATABASE_TABLE WHERE $DIC_ID='$id' LIMIT 1)"
        val cursor = database.rawQuery(sql, null)
        cursor.moveToFirst()
        return if (cursor.getInt(0) == 1) {
            cursor.close()
            true
        } else {
            cursor.close()
            false
        }
    }
}