package com.alfiyah.dictionaryapps.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.alfiyah.dictionaryapps.db.DatabaseContract.DictionaryColumns
import com.alfiyah.dictionaryapps.db.DatabaseContract.DictionaryColumns.Companion.TABLE_NAME

internal class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "dbdictionary"
        private const val DATABASE_VERSION = 1

        private const val SQL_CREATE_TABLE = "CREATE TABLE $TABLE_NAME" +
                " (${DictionaryColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${DictionaryColumns.DIC_ID} TEXT," +
                " ${DictionaryColumns.DIC_WORD} TEXT," +
                " ${DictionaryColumns.DIC_TERM} TEXT," +
                " ${DictionaryColumns.DIC_SYNTAX} TEXT," +
                " ${DictionaryColumns.DIC_EXP_SYN} TEXT," +
                " ${DictionaryColumns.DIC_EXP_DESC} TEXT)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVer: Int, newVer: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}